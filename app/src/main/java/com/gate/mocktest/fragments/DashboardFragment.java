package com.gate.mocktest.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.gate.mocktest.activities.LoginActivity;
import com.gate.mocktest.adapters.WeakTopicAdapter;
import com.gate.mocktest.database.AppDatabase;
import com.gate.mocktest.database.entities.User;
import com.gate.mocktest.databinding.FragmentDashboardBinding;
import com.gate.mocktest.models.WeakTopic;
import com.gate.mocktest.viewmodels.DashboardViewModel;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding b;
    private DashboardViewModel vm;
    private User currentUser;
    @Override public View onCreateView(@NonNull LayoutInflater i,ViewGroup c,Bundle s){b=FragmentDashboardBinding.inflate(i,c,false);return b.getRoot();}
    @Override public void onViewCreated(@NonNull View v,@Nullable Bundle s){
        super.onViewCreated(v,s);
        vm=new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        AppDatabase.databaseWriteExecutor.execute(()->{
            currentUser=AppDatabase.getInstance(requireContext()).userDao().getLoggedInUser();
            requireActivity().runOnUiThread(()->{
                if(currentUser!=null){
                    vm.init(currentUser.id);
                    b.tvName.setText(currentUser.name);
                    b.tvBranch.setText(currentUser.branch+" · GATE "+currentUser.targetYear);
                    String initials=currentUser.name.length()>1?currentUser.name.substring(0,2).toUpperCase():currentUser.name.toUpperCase();
                    b.tvInitials.setText(initials);
                    b.tvStreak.setText("0-day streak");b.tvRank.setText("Rank: Unranked");
                    setupStats();
                }
            });
        });
        setupWeakTopics();
        b.btnLogout.setOnClickListener(vv->{
            AppDatabase.databaseWriteExecutor.execute(()->{AppDatabase.getInstance(requireContext()).userDao().logoutAll();
                requireActivity().runOnUiThread(()->{startActivity(new Intent(getActivity(),LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));requireActivity().finish();});
            });
        });
    }
    private void setupStats(){
        vm.totalTests.observe(getViewLifecycleOwner(),c->b.tvTotalTests.setText(c!=null?String.valueOf(c):"0"));
        vm.overallAccuracy.observe(getViewLifecycleOwner(),a->{float acc=a!=null?a:0f;b.tvOverallAccuracy.setText(String.format("%.1f%%",acc));b.tvAccuracyPct.setText(String.format("%.1f%%",acc));b.progressAccuracy.setProgress((int)acc);});
        vm.totalStudyTime.observe(getViewLifecycleOwner(),ms->{if(ms==null||ms==0){b.tvStudyTime.setText("0h");return;}long h=TimeUnit.MILLISECONDS.toHours(ms),m=TimeUnit.MILLISECONDS.toMinutes(ms)%60;b.tvStudyTime.setText(h>0?h+"h "+m+"m":m+"m");});
        vm.recentAttempts.observe(getViewLifecycleOwner(),list->{int total=0;if(list!=null)for(com.gate.mocktest.database.entities.TestAttempt a:list)total+=a.totalQuestions;b.tvTotalQuestions.setText(String.valueOf(total));});
    }
    private void setupWeakTopics(){
        List<WeakTopic> weak=Arrays.asList(new WeakTopic("Complete a test to see weak areas",0f));
        WeakTopicAdapter wa=new WeakTopicAdapter();
        b.rvWeakTopics.setLayoutManager(new LinearLayoutManager(getContext()));b.rvWeakTopics.setAdapter(wa);wa.submitList(weak);
    }
    @Override public void onDestroyView(){super.onDestroyView();b=null;}
}
