package com.gate.mocktest.fragments;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.gate.mocktest.adapters.RecentTestAdapter;
import com.gate.mocktest.database.AppDatabase;
import com.gate.mocktest.database.entities.User;
import com.gate.mocktest.databinding.FragmentResultsBinding;
import com.gate.mocktest.viewmodels.DashboardViewModel;
public class ResultsFragment extends Fragment {
    private FragmentResultsBinding b;
    @Override public View onCreateView(@NonNull LayoutInflater i,ViewGroup c,Bundle s){b=FragmentResultsBinding.inflate(i,c,false);return b.getRoot();}
    @Override public void onViewCreated(@NonNull View v,@Nullable Bundle s){
        super.onViewCreated(v,s);
        DashboardViewModel vm=new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        RecentTestAdapter adapter=new RecentTestAdapter(a->{});
        b.rvResults.setLayoutManager(new LinearLayoutManager(getContext()));b.rvResults.setAdapter(adapter);
        AppDatabase.databaseWriteExecutor.execute(()->{
            User user=AppDatabase.getInstance(requireContext()).userDao().getLoggedInUser();
            requireActivity().runOnUiThread(()->{
                if(user!=null){vm.init(user.id);vm.recentAttempts.observe(getViewLifecycleOwner(),list->{
                    adapter.submitList(list);
                    boolean empty=list==null||list.isEmpty();
                    b.tvNoResults.setVisibility(empty?View.VISIBLE:View.GONE);
                    b.rvResults.setVisibility(empty?View.GONE:View.VISIBLE);
                });}
            });
        });
    }
    @Override public void onDestroyView(){super.onDestroyView();b=null;}
}
