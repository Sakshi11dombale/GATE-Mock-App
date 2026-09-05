package com.gate.mocktest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.gate.mocktest.activities.ExamActivity;
import com.gate.mocktest.adapters.TestCardAdapter;
import com.gate.mocktest.database.AppDatabase;
import com.gate.mocktest.database.entities.User;
import com.gate.mocktest.databinding.FragmentTestsBinding;
import com.gate.mocktest.models.ExamSession;
import com.gate.mocktest.models.TestCardItem;
import com.gate.mocktest.utils.BranchUtil;
import com.gate.mocktest.utils.JsonQuestionLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TestsFragment extends Fragment {

    private FragmentTestsBinding b;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        b = FragmentTestsBinding.inflate(
                inflater,
                container,
                false
        );

        return b.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {

        super.onViewCreated(
                view,
                savedInstanceState
        );

        AppDatabase.databaseWriteExecutor.execute(() -> {

            currentUser =
                    AppDatabase.getInstance(
                                    requireContext()
                            )
                            .userDao()
                            .getLoggedInUser();

            if (!isAdded()) {
                return;
            }

            requireActivity().runOnUiThread(() -> {

                if (b == null) {
                    return;
                }

                String branch =
                        currentUser != null
                                ? normalizeBranch(
                                currentUser.branch
                        )
                                : "DA";

                b.tvBranchLabel.setText(
                        "Branch: "
                                + displayBranch(
                                branch
                        )
                );

                setupMockTests(
                        branch
                );

                setupPYQ(
                        branch
                );
            });
        });
    }

    private void setupMockTests(String branch) {

        List<TestCardItem> mocks = Arrays.asList(

                new TestCardItem(
                        "Practice Mock 01",
                        "30 Questions · 60 min",
                        "EASY",
                        false,
                        0
                ),

                new TestCardItem(
                        "Practice Mock 02",
                        "30 Questions · 60 min",
                        "MEDIUM",
                        false,
                        0
                ),

                new TestCardItem(
                        "Practice Mock 03",
                        "30 Questions · 60 min",
                        "HARD",
                        false,
                        0
                )
        );

        TestCardAdapter adapter =
                new TestCardAdapter(item ->
                        launch(
                                ExamSession.TYPE_MOCK,
                                "",
                                30,
                                60 * 60 * 1000L
                        )
                );

        b.rvMockTests.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        b.rvMockTests.setAdapter(adapter);

        adapter.submitList(mocks);
    }

    private void setupPYQ(String branch) {

        List<Integer> years =
                JsonQuestionLoader.getAvailablePYQYears(
                        requireContext(),
                        branch
                );

        List<TestCardItem> pyqs =
                new ArrayList<>();

        for (Integer year : years) {

            pyqs.add(
                    new TestCardItem(
                            "GATE " + year + " " + branch + " PYQ",
                            "Previous-year paper · 3 hrs",
                            "PYQ",
                            false,
                            0
                    )
            );
        }

        TestCardAdapter adapter =
                new TestCardAdapter(item -> {

                    int index = pyqs.indexOf(item);

                    if (index >= 0 && index < years.size()) {

                        launchPYQ(
                                branch,
                                years.get(index)
                        );
                    }
                });

        b.rvPyqTests.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        b.rvPyqTests.setAdapter(adapter);

        adapter.submitList(pyqs);
    }
    private String normalizeBranch(
            String branch
    ) {

        return BranchUtil.normalize(
                branch
        );
    }

    private String displayBranch(
            String branch
    ) {

        return BranchUtil.display(
                branch
        );
    }

    private void launch(
            String type,
            String subject,
            int count,
            long duration
    ) {

        if (currentUser == null) {
            return;
        }

        Intent intent =
                new Intent(
                        getActivity(),
                        ExamActivity.class
                );

        intent.putExtra(
                ExamActivity.EXTRA_TEST_TYPE,
                type
        );

        intent.putExtra(
                ExamActivity.EXTRA_BRANCH,
                normalizeBranch(
                        currentUser.branch
                )
        );

        intent.putExtra(
                ExamActivity.EXTRA_SUBJECT,
                subject
        );

        intent.putExtra(
                ExamActivity.EXTRA_Q_COUNT,
                count
        );

        intent.putExtra(
                ExamActivity.EXTRA_DURATION,
                duration
        );

        intent.putExtra(
                ExamActivity.EXTRA_USER_ID,
                currentUser.id
        );

        startActivity(
                intent
        );
    }

    private void launchPYQ(
            String branch,
            int year
    ) {

        if (currentUser == null) {
            return;
        }

        Intent intent =
                new Intent(
                        getActivity(),
                        ExamActivity.class
                );

        intent.putExtra(
                ExamActivity.EXTRA_TEST_TYPE,
                ExamSession.TYPE_PYQ
        );

        intent.putExtra(
                ExamActivity.EXTRA_BRANCH,
                normalizeBranch(
                        branch
                )
        );

        intent.putExtra(
                ExamActivity.EXTRA_YEAR,
                year
        );

        intent.putExtra(
                ExamActivity.EXTRA_DURATION,
                3 * 60 * 60 * 1000L
        );

        intent.putExtra(
                ExamActivity.EXTRA_USER_ID,
                currentUser.id
        );

        startActivity(
                intent
        );
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        b = null;
    }

    private int getSpanCount() {

        int screenWidth =
                getResources()
                        .getDisplayMetrics()
                        .widthPixels;

        float density =
                getResources()
                        .getDisplayMetrics()
                        .density;

        float screenWidthDp =
                screenWidth / density;

        // Small mobile
        if (screenWidthDp < 600) {
            return 1;
        }

        // Tablet
        if (screenWidthDp < 900) {
            return 2;
        }

        // Large screen
        return 3;
    }

}