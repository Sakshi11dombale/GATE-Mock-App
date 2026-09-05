package com.gate.mocktest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.gate.mocktest.R;
import com.gate.mocktest.activities.ExamActivity;
import com.gate.mocktest.adapters.RecentTestAdapter;
import com.gate.mocktest.database.AppDatabase;
import com.gate.mocktest.database.entities.User;
import com.gate.mocktest.databinding.FragmentHomeBinding;
import com.gate.mocktest.models.ExamSession;
import com.gate.mocktest.utils.BranchUtil;
import com.gate.mocktest.viewmodels.DashboardViewModel;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding b;
    private DashboardViewModel vm;
    private User currentUser;

    @Override public View onCreateView(@NonNull LayoutInflater i, ViewGroup c, Bundle s) {
        b = FragmentHomeBinding.inflate(i, c, false);
        return b.getRoot();
    }

    @Override public void onViewCreated(@NonNull View v, @Nullable Bundle s) {
        super.onViewCreated(v, s);
        vm = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        RecentTestAdapter adapter = new RecentTestAdapter(a -> {});
        b.rvRecentTests.setLayoutManager(new LinearLayoutManager(getContext()));
        b.rvRecentTests.setAdapter(adapter);

        b.cardMock.setOnClickListener(vv -> launch(ExamSession.TYPE_MOCK, "", 30, 60 * 60 * 1000L));
        b.cardTopic.setOnClickListener(vv -> NavHostFragment.findNavController(this).navigate(R.id.topicsFragment));
        b.cardPyq.setOnClickListener(vv -> NavHostFragment.findNavController(this).navigate(R.id.testsFragment));

        AppDatabase.databaseWriteExecutor.execute(() -> {
            currentUser = AppDatabase.getInstance(requireContext()).userDao().getLoggedInUser();
            if (!isAdded() || currentUser == null) return;
            requireActivity().runOnUiThread(() -> {
                if (!isAdded() || b == null) return;
                vm.init(currentUser.id);
                b.tvWelcome.setText("Hi, " + currentUser.name + " 👋");
                b.tvBranch.setText(displayBranch(currentUser.branch) + "  •  GATE " + currentUser.targetYear);
                setupObservers();
            });
        });
    }

    private String displayBranch(String branch) {
        if (branch == null) return "CSE";
        if ("CS".equals(branch)) return "CSE";
        if ("EC".equals(branch)) return "ECE";
        return branch;
    }

    private void setupObservers() {
        vm.totalTests.observe(getViewLifecycleOwner(), c -> b.tvTestsDone.setText(c != null ? String.valueOf(c) : "0"));
        vm.overallAccuracy.observe(getViewLifecycleOwner(), a -> b.tvAccuracy.setText(a != null ? String.format("%.0f%%", a) : "0%"));
        b.tvStreak.setText("0");
    }

    private String normalizeBranch(String branch) { return BranchUtil.normalize(branch); }


    private void launch(String type, String subject, int count, long dur) {
        if (currentUser == null) return;
        Intent i = new Intent(getActivity(), ExamActivity.class);
        i.putExtra(ExamActivity.EXTRA_TEST_TYPE, type);
        i.putExtra(ExamActivity.EXTRA_BRANCH, normalizeBranch(currentUser.branch));
        i.putExtra(ExamActivity.EXTRA_SUBJECT, subject);
        i.putExtra(ExamActivity.EXTRA_Q_COUNT, count);
        i.putExtra(ExamActivity.EXTRA_DURATION, dur);
        i.putExtra(ExamActivity.EXTRA_USER_ID, currentUser.id);
        startActivity(i);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        b = null;
    }
}
