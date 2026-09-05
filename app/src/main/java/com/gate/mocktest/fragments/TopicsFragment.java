package com.gate.mocktest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import com.gate.mocktest.activities.ExamActivity;
import com.gate.mocktest.adapters.TopicAdapter;
import com.gate.mocktest.database.AppDatabase;
import com.gate.mocktest.database.entities.User;
import com.gate.mocktest.databinding.FragmentTopicsBinding;
import com.gate.mocktest.models.*;
import com.gate.mocktest.utils.BranchUtil;
import java.util.*;

public class TopicsFragment extends Fragment {
    private FragmentTopicsBinding b;
    private User currentUser;
    private static final Map<String,List<TopicItem>> TOPICS_BY_BRANCH=new HashMap<>();

    static {
        put("CS", new String[][]{
            {"Engineering Mathematics","Medium"},{"Digital Logic","Easy"},{"Computer Organization","Medium"},
            {"Programming & Data Structures","Medium"},{"Algorithms","Hard"},{"Theory of Computation","Hard"},
            {"Compiler Design","Hard"},{"Operating Systems","Medium"},{"DBMS","Medium"},{"Computer Networks","Hard"}
        });
        put("EC", new String[][]{
            {"Engineering Mathematics","Medium"},{"Networks","Medium"},{"Signals & Systems","Hard"},
            {"Electronic Devices","Medium"},{"Analog Circuits","Hard"},{"Digital Circuits","Medium"},
            {"Control Systems","Hard"},{"Communications","Hard"},{"Electromagnetics","Hard"}
        });
        put("EE", new String[][]{
            {"Engineering Mathematics","Medium"},{"Electric Circuits","Medium"},{"Signals & Systems","Hard"},
            {"Electrical Machines","Hard"},{"Power Systems","Hard"},{"Control Systems","Hard"},
            {"Power Electronics","Medium"},{"Analog & Digital Electronics","Medium"},{"Electromagnetic Fields","Hard"}
        });
        put("ME", new String[][]{
            {"Engineering Mathematics","Medium"},{"Engineering Mechanics","Medium"},{"Strength of Materials","Hard"},
            {"Theory of Machines","Hard"},{"Thermodynamics","Medium"},{"Fluid Mechanics","Medium"},
            {"Heat Transfer","Hard"},{"Manufacturing Engineering","Medium"},{"Industrial Engineering","Easy"}
        });
        put("CE", new String[][]{
            {"Engineering Mathematics","Medium"},{"Engineering Mechanics","Medium"},{"Structural Engineering","Hard"},
            {"Geotechnical Engineering","Hard"},{"Environmental Engineering","Medium"},{"Transportation Engineering","Medium"},
            {"Water Resources Engineering","Medium"},{"Surveying","Easy"},{"Construction Management","Easy"}
        });
        put("IN", new String[][]{
            {"Engineering Mathematics","Medium"},{"Electrical Circuits","Medium"},{"Signals & Systems","Hard"},
            {"Control Systems","Hard"},{"Measurements","Medium"},{"Sensors","Medium"},{"Analog Electronics","Hard"},
            {"Digital Electronics","Medium"},{"Communication Systems","Hard"}
        });
        put("CH", new String[][]{
            {"Engineering Mathematics","Medium"},{"Process Calculations","Medium"},{"Fluid Mechanics","Hard"},
            {"Heat Transfer","Hard"},{"Mass Transfer","Hard"},{"Chemical Reaction Engineering","Hard"},
            {"Thermodynamics","Medium"},{"Process Control","Hard"}
        });
        put("BT", new String[][]{
            {"Biochemistry","Medium"},{"Microbiology","Medium"},{"Molecular Biology","Hard"},{"Genetics","Medium"},
            {"Cell Biology","Medium"},{"Bioprocess Engineering","Hard"},{"Immunology","Medium"}
        });
        put("DA", new String[][]{
            {"Probability & Statistics","Medium"},{"Linear Algebra","Medium"},{"Calculus","Medium"},
            {"Programming","Easy"},{"Data Structures","Medium"},{"Algorithms","Hard"},{"Database Management","Medium"},
            {"Machine Learning","Hard"},{"Artificial Intelligence","Hard"}
        });
        put("AE", new String[][]{
            {"Engineering Mathematics","Medium"},{"Aerodynamics","Hard"},{"Flight Mechanics","Hard"},
            {"Aircraft Structures","Hard"},{"Propulsion","Hard"},{"Aircraft Design","Hard"}
        });
        put("AG", new String[][]{
            {"Engineering Mathematics","Medium"},{"Farm Machinery","Medium"},{"Soil & Water Engineering","Medium"},
            {"Agricultural Processing","Medium"},{"Irrigation Engineering","Hard"},{"Agricultural Structures","Medium"}
        });
        put("AR", new String[][]{
            {"Architecture Design","Hard"},{"Building Construction","Medium"},{"Building Services","Medium"},
            {"Urban Planning","Medium"},{"Environmental Planning","Medium"},{"Structural Systems","Hard"}
        });
        put("BM", new String[][]{
            {"Biomedical Instrumentation","Hard"},{"Signals & Systems","Hard"},{"Medical Imaging","Medium"},
            {"Biomaterials","Medium"},{"Biomechanics","Hard"},{"Human Anatomy","Medium"}
        });
        put("CY", new String[][]{
            {"Physical Chemistry","Hard"},{"Organic Chemistry","Medium"},{"Inorganic Chemistry","Medium"},
            {"Quantum Chemistry","Hard"},{"Spectroscopy","Hard"},{"Chemical Thermodynamics","Medium"}
        });
        put("ES", new String[][]{
            {"Environmental Chemistry","Medium"},{"Environmental Microbiology","Medium"},{"Water Pollution","Medium"},
            {"Air Pollution","Medium"},{"Solid Waste Management","Medium"},{"Environmental Management","Medium"}
        });
        put("EY", new String[][]{
            {"Ecology","Medium"},{"Evolution","Hard"},{"Population Biology","Medium"},{"Biodiversity","Medium"},
            {"Conservation Biology","Medium"},{"Environmental Biology","Medium"}
        });
        put("GE", new String[][]{
            {"Surveying","Medium"},{"Remote Sensing","Hard"},{"GIS","Hard"},{"Photogrammetry","Hard"},{"Cartography","Medium"}
        });
        put("GG", new String[][]{
            {"Geology","Medium"},{"Mineralogy","Medium"},{"Petrology","Hard"},{"Structural Geology","Hard"},{"Geophysics","Hard"}
        });
        put("MA", new String[][]{
            {"Linear Algebra","Medium"},{"Calculus","Medium"},{"Real Analysis","Hard"},{"Complex Analysis","Hard"},
            {"Differential Equations","Hard"},{"Probability & Statistics","Medium"},{"Numerical Analysis","Medium"}
        });
        put("MN", new String[][]{
            {"Mining Engineering","Hard"},{"Mine Development","Medium"},{"Rock Mechanics","Hard"},
            {"Mine Ventilation","Hard"},{"Mineral Processing","Medium"}
        });
        put("MT", new String[][]{
            {"Metallurgical Thermodynamics","Hard"},{"Physical Metallurgy","Hard"},{"Extractive Metallurgy","Hard"},
            {"Mechanical Metallurgy","Medium"},{"Materials Science","Medium"}
        });
        put("NM", new String[][]{
            {"Engineering Mathematics","Medium"},{"Hydrostatics","Hard"},{"Marine Structures","Hard"},
            {"Ship Resistance","Hard"},{"Marine Engineering","Medium"},{"Ship Design","Hard"}
        });
        put("PE", new String[][]{
            {"Petroleum Exploration","Medium"},{"Reservoir Engineering","Hard"},{"Drilling Engineering","Hard"},
            {"Production Engineering","Hard"},{"Petroleum Geology","Medium"}
        });
        put("PH", new String[][]{
            {"Mathematical Physics","Hard"},{"Classical Mechanics","Hard"},{"Electromagnetic Theory","Hard"},
            {"Quantum Mechanics","Hard"},{"Thermodynamics","Medium"},{"Solid State Physics","Hard"},{"Nuclear Physics","Medium"}
        });
        put("PI", new String[][]{
            {"Engineering Mathematics","Medium"},{"Operations Research","Hard"},{"Manufacturing Processes","Medium"},
            {"Industrial Engineering","Medium"},{"Production Planning","Hard"},{"Quality Engineering","Medium"}
        });
        put("ST", new String[][]{
            {"Probability","Medium"},{"Statistical Inference","Hard"},{"Regression","Medium"},{"Linear Models","Hard"},
            {"Sampling Theory","Medium"},{"Time Series","Hard"}
        });
        put("TF", new String[][]{
            {"Textile Fibres","Medium"},{"Yarn Manufacturing","Medium"},{"Fabric Manufacturing","Medium"},
            {"Textile Chemical Processing","Hard"},{"Textile Testing","Medium"}
        });
        put("XE", new String[][]{
            {"Engineering Mathematics","Medium"},{"Fluid Mechanics","Medium"},{"Materials Science","Medium"},
            {"Thermodynamics","Hard"},{"Engineering Mechanics","Medium"}
        });
        put("XH", new String[][]{
            {"Reasoning & Comprehension","Medium"},{"Economics","Medium"},{"English","Medium"},
            {"Linguistics","Hard"},{"Philosophy","Hard"},{"Psychology","Medium"},{"Sociology","Medium"}
        });
        put("XL", new String[][]{
            {"Chemistry","Medium"},{"Biochemistry","Medium"},{"Botany","Medium"},{"Microbiology","Medium"},
            {"Zoology","Medium"},{"Food Technology","Medium"},{"Ecology","Medium"}
        });

        // Compatibility for users created with the old project version.
        TOPICS_BY_BRANCH.put("CSE", TOPICS_BY_BRANCH.get("CS"));
        TOPICS_BY_BRANCH.put("ECE", TOPICS_BY_BRANCH.get("EC"));
    }

    private static void put(String branch, String[][] data) {
        List<TopicItem> topics=new ArrayList<>();
        for(int i=0;i<data.length;i++) topics.add(new TopicItem(data[i][0],branch,15+i%4*5,data[i][1]));
        TOPICS_BY_BRANCH.put(branch,topics);
    }

    @Override public View onCreateView(@NonNull LayoutInflater i,ViewGroup c,Bundle s){
        b=FragmentTopicsBinding.inflate(i,c,false);return b.getRoot();
    }

    @Override public void onViewCreated(@NonNull View v,@Nullable Bundle s){
        super.onViewCreated(v,s);
        AppDatabase.databaseWriteExecutor.execute(()->{
            currentUser=AppDatabase.getInstance(requireContext()).userDao().getLoggedInUser();
            String branch=currentUser!=null?normalizeBranch(currentUser.branch):"CS";
            List<TopicItem> baseTopics=TOPICS_BY_BRANCH.getOrDefault(branch,TOPICS_BY_BRANCH.get("CS"));
            List<TopicItem> topics=new ArrayList<>();
            com.gate.mocktest.database.dao.QuestionDao dao =
                    AppDatabase.getInstance(requireContext()).questionDao();

            for (TopicItem t : baseTopics) {
                String query = resolveTopicQuery(t.name);
                int available = dao.getTopicQuestionCount(t.name, query, branch);
                topics.add(new TopicItem(t.name, branch, available, t.difficulty));
            }

            requireActivity().runOnUiThread(()->{
                if (!isAdded() || b == null) return;
                b.tvBranchLabel.setText("Branch: "+branch);
                TopicAdapter adapter=new TopicAdapter(topic->launchTopic(topic));
                b.rvTopics.setLayoutManager(new GridLayoutManager(getContext(),2));
                b.rvTopics.setAdapter(adapter);
                adapter.submitList(topics);
            });
        });
    }

    private String normalizeBranch(String branch){ return BranchUtil.normalize(branch); }

    private String resolveTopicQuery(String topic) {
        if (topic == null) return "";
        switch (topic) {
            case "Programming & Data Structures": return "Data Structures";
            case "Networks": return "Network Theory";
            case "Signals & Systems": return "Signals and Systems";
            case "Electronic Devices": return "Electronics";
            case "Analog Circuits": return "Analog Circuits";
            case "Digital Circuits": return "Digital Electronics";
            case "Electromagnetics": return "Electromagnetics";
            case "Electric Circuits": return "Circuit Theory";
            case "Analog & Digital Electronics": return "Electronics";
            case "Electromagnetic Fields": return "Electromagnetic Theory";
            case "Manufacturing Engineering": return "Manufacturing";
            case "Industrial Engineering": return "Industrial Engineering";
            case "Water Resources Engineering": return "Water Resources";
            case "Transportation Engineering": return "Transportation";
            case "Construction Management": return "Construction Management";
            default: return topic;
        }
    }

    private void launchTopic(TopicItem t){
        if(currentUser==null)return;
        String branch = normalizeBranch(currentUser.branch);
        String query = resolveTopicQuery(t.name);
        Intent i=new Intent(getActivity(),ExamActivity.class);
        i.putExtra(ExamActivity.EXTRA_TEST_TYPE,ExamSession.TYPE_TOPIC);
        i.putExtra(ExamActivity.EXTRA_BRANCH,branch);
        i.putExtra(ExamActivity.EXTRA_SUBJECT,t.name);
        i.putExtra(ExamActivity.EXTRA_TOPIC_QUERY,query);
        i.putExtra(ExamActivity.EXTRA_Q_COUNT,15);i.putExtra(ExamActivity.EXTRA_DURATION,40*60*1000L);
        i.putExtra(ExamActivity.EXTRA_USER_ID,currentUser.id);startActivity(i);
    }

    @Override public void onDestroyView(){super.onDestroyView();b=null;}
}
