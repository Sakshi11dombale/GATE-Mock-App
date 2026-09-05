package com.gate.mocktest.utils;

import android.content.Context;
import com.gate.mocktest.database.dao.QuestionDao;
import com.gate.mocktest.database.entities.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.*;

/** JSON question-bank repository. Each PYQ file is independent from Room. */
public final class JsonQuestionLoader {
    private static final String ASSET_DIR = "questions";
    private static final Gson GSON = new Gson();
    private static final Type QUESTION_LIST_TYPE = new TypeToken<List<Question>>(){}.getType();
    private JsonQuestionLoader(){}

    public static List<Question> loadAll(Context context){
        List<Question> all=new ArrayList<>(); loadRecursive(context,ASSET_DIR,all); return all;
    }
    private static void loadRecursive(Context c,String dir,List<Question> out){
        try{
            String[] names=c.getAssets().list(dir); if(names==null)return;
            for(String name:names){
                String path=dir+"/"+name; String[] child=c.getAssets().list(path);
                if(child==null||child.length==0){ if(name.toLowerCase(Locale.ROOT).endsWith(".json")) out.addAll(readFile(c,path)); }
                else loadRecursive(c,path,out);
            }
        }catch(Exception ignored){}
    }
    private static List<Question> readFile(Context c,String path){
        try(InputStream in=c.getAssets().open(path); Reader r=new BufferedReader(new InputStreamReader(in,"UTF-8"))){
            List<Question> q=GSON.fromJson(r,QUESTION_LIST_TYPE); return q==null?new ArrayList<>():q;
        }catch(Exception e){ return new ArrayList<>(); }
    }
    private static String norm(String b){ return b==null?"":BranchUtil.normalize(b).trim().toUpperCase(Locale.ROOT); }

    /** Finds years from filenames AND accepts DA_2024.json, da_2024.json, or any *_2024.json. */
    public static List<Integer> getAvailablePYQYears(Context c,String branch){
        Set<Integer> years=new TreeSet<>(Collections.reverseOrder());
        String dir=findBranchPyqDirectory(c,norm(branch));
        if(dir==null)return new ArrayList<>();
        try{
            String[] files=c.getAssets().list(dir); if(files==null)return new ArrayList<>();
            Pattern p=Pattern.compile(".*?(20\\d{2}).*?\\.json$",Pattern.CASE_INSENSITIVE);
            for(String f:files){ Matcher m=p.matcher(f); if(m.matches()) years.add(Integer.parseInt(m.group(1))); }
        }catch(Exception ignored){}
        return new ArrayList<>(years);
    }
    private static String findBranchPyqDirectory(Context c,String branch){
        try{
            String[] roots=c.getAssets().list(ASSET_DIR); if(roots==null)return null;
            for(String root:roots) if(root.equalsIgnoreCase(branch)){
                String[] children=c.getAssets().list(ASSET_DIR+"/"+root); if(children!=null)
                    for(String child:children) if(child.equalsIgnoreCase("PYQ")) return ASSET_DIR+"/"+root+"/"+child;
            }
        }catch(Exception ignored){}
        return null;
    }
    /** Loads exactly one year's paper, independent of Room and filename capitalization. */
    public static List<Question> loadPYQ(Context c,String branch,int year){
        String dir=findBranchPyqDirectory(c,norm(branch)); if(dir==null)return new ArrayList<>();
        try{
            String[] files=c.getAssets().list(dir); if(files==null)return new ArrayList<>();
            for(String f:files){
                if(!f.toLowerCase(Locale.ROOT).endsWith(".json"))continue;
                Matcher m=Pattern.compile(".*?(20\\d{2}).*?\\.json$",Pattern.CASE_INSENSITIVE).matcher(f);
                if(m.matches()&&Integer.parseInt(m.group(1))==year) return readFile(c,dir+"/"+f);
            }
        }catch(Exception ignored){}
        return new ArrayList<>();
    }
    /** Non-PYQ branch practice bank plus common aptitude, used for full mocks. */
    public static List<Question> loadMockBank(Context c,String branch){
        String b=norm(branch); List<Question> all=new ArrayList<>();
        all.addAll(loadBranchRoot(c,b)); all.addAll(loadBranchTopics(c,b)); all.addAll(loadCommonAptitude(c));
        return unique(all);
    }
    private static List<Question> loadBranchRoot(Context c,String b){
        try{String[] roots=c.getAssets().list(ASSET_DIR);if(roots!=null)for(String r:roots)if(r.equalsIgnoreCase(b)&&r.toLowerCase(Locale.ROOT).endsWith(".json"))return readFile(c,ASSET_DIR+"/"+r);}catch(Exception ignored){}return new ArrayList<>();}
    private static List<Question> loadBranchTopics(Context c,String b){
        List<Question> out=new ArrayList<>();try{String[] roots=c.getAssets().list(ASSET_DIR);if(roots!=null)for(String r:roots)if(r.equalsIgnoreCase(b)){String base=ASSET_DIR+"/"+r;String[] kids=c.getAssets().list(base);if(kids!=null)for(String k:kids)if(k.equalsIgnoreCase("topics"))loadRecursive(c,base+"/"+k,out);}}catch(Exception ignored){}return out;}
    private static List<Question> loadCommonAptitude(Context c){try{String[] fs=c.getAssets().list(ASSET_DIR);if(fs!=null)for(String f:fs)if(f.equalsIgnoreCase("GENERAL_APTITUDE.json"))return readFile(c,ASSET_DIR+"/"+f);}catch(Exception ignored){}return new ArrayList<>();}
    public static List<Question> loadTopic(Context c,String branch,String query){
        String q=query==null?"":query.trim().toLowerCase(Locale.ROOT); List<Question> out=new ArrayList<>();
        for(Question x:loadMockBank(c,branch)) if((x.subject!=null&&x.subject.toLowerCase(Locale.ROOT).contains(q))||(x.topic!=null&&x.topic.toLowerCase(Locale.ROOT).contains(q))) out.add(x);
        return unique(out);
    }
    private static List<Question> unique(List<Question> in){LinkedHashMap<String,Question> m=new LinkedHashMap<>();for(Question q:in){if(q!=null&&q.questionText!=null)m.put(q.questionText,q);}return new ArrayList<>(m.values());}
    public static int seedFromAssets(Context c,QuestionDao dao){List<Question> q=loadAll(c);if(!q.isEmpty())dao.insertAll(q);return q.size();}
}
