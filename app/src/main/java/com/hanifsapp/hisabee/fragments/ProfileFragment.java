package com.hanifsapp.hisabee.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel;
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType;
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hanifsapp.hisabee.databinding.DialogueChartBinding;
import com.hanifsapp.hisabee.databinding.DialogueInfoInputBinding;
import com.hanifsapp.hisabee.databinding.FragmentProfileBinding;
import com.hanifsapp.hisabee.utility.GetDate;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    String DB_NAME = "NAME";
    String DB_ADDRESS = "ADDRESS";
    String DB_NUMBER = "NUMBER";
    String DB_CURRENCY = "CURRENCY";
    String ChartTag = "";
    private AAChartModel profileChart;
    private boolean isSellCalled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        setInfos();
        binding.btnEdit.setOnClickListener(v -> showAddCustomerDialog());

        binding.monthCostBtn.setOnClickListener(v -> {
            ChartTag = "This Month Cost";
            isSellCalled = false;
            showChart();
        });
        binding.monthSellBtn.setOnClickListener(v -> {
            ChartTag = "This Month Sell";
            isSellCalled = true;
            showChart();
        });
        return binding.getRoot();
    }


    private void setInfos() {

        SharedPreferences prefs = getActivity().getSharedPreferences("Submit", Context.MODE_PRIVATE);


        binding.textViewName.setText(prefs.getString(DB_NAME, "Add Your Name"));
        binding.textViewPhoneNumber.setText(prefs.getString(DB_NUMBER, "Add Phone Number"));
        binding.textViewAddress.setText(prefs.getString(DB_ADDRESS, "Add Address"));
        binding.textViewCurrency.setText(prefs.getString(DB_CURRENCY, "Add Currency"));

    }


    private void showAddCustomerDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        DialogueInfoInputBinding dialogBinding = DialogueInfoInputBinding.inflate(getLayoutInflater());
        View dialogView = dialogBinding.getRoot();
        dialogBuilder.setView(dialogView);


        dialogBuilder.setPositiveButton("Submit", (dialog, which) -> {
            SharedPreferences preferences = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(DB_NAME, dialogBinding.inputName.getText().toString());
            editor.putString(DB_ADDRESS, dialogBinding.inputAddress.getText().toString());
            editor.putString(DB_NUMBER, dialogBinding.inputNumber.getText().toString());
            editor.putString(DB_CURRENCY, dialogBinding.inputCureency.getText().toString());
            editor.apply();


        });

        dialogBuilder.setNegativeButton("Cancel", null);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        setInfos();
    }




    private DialogueChartBinding binding1;
    private void showChart() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        binding1 = DialogueChartBinding.inflate(getLayoutInflater());
        binding1.textViewDates.setText(GetDate.getMonth(0));
        dialog.setContentView(binding1.getRoot());
        DisplayChart();
        dialog.show();


        binding1.btnBacks.setOnClickListener(v -> {
            if (isSellCalled){
                GetHistory.getMonthSell(-1);
            }else {
                GetHistory.getMonthCost(-1);
            }
            binding1.textViewDates.setText(GetDate.Month);
        });

        binding1.btnForwords.setOnClickListener(v -> {
            if (isSellCalled){
                GetHistory.getMonthSell(1);
            }else {
                GetHistory.getMonthCost(1);
            }

            binding1.textViewDates.setText(GetDate.Month);
        });


        GetHistory.thisMonthSell.observe(getActivity(), seriesArray -> {
            binding1.chartviewProfile.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(new AASeriesElement[]{
                    new AASeriesElement()
                            .data(seriesArray.toArray())
            });
        });


        GetHistory.thisMonthCost.observe(getActivity(), seriesArray -> {
            binding1.chartviewProfile.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(new AASeriesElement[]{
                    new AASeriesElement()
                            .data(seriesArray.toArray())
            });
        });

    }


    private void DisplayChart() {
        profileChart = new AAChartModel()
                .chartType(AAChartType.Column)
                .backgroundColor("")
                .categories(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"})
                .axesTextColor("#FFBB86FC")
                .dataLabelsEnabled(true)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name(ChartTag)
                                .data(new Object[]{0, 0, 0, 0, 0, 0, 0, 0, 0})

                });
        binding1.chartviewProfile.aa_drawChartWithChartModel(profileChart);


    }

}