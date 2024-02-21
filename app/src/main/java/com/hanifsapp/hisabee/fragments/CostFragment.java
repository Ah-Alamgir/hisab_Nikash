package com.hanifsapp.hisabee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel;
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType;
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hanifsapp.hisabee.R;
import com.hanifsapp.hisabee.databinding.DialogAddCustomerBinding;
import com.hanifsapp.hisabee.databinding.FragmentCostBinding;
import com.hanifsapp.hisabee.firebase_Db.Constant;
import com.hanifsapp.hisabee.model.CostHistory;
import com.hanifsapp.hisabee.utility.GetDate;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CostFragment extends Fragment {
    private FragmentCostBinding binding;
    private ArrayList<CostHistory> history = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCostBinding.inflate(inflater, container, false);


        binding.textViewDate.setText(GetDate.getDate(0));
        GetHistory.getCostHistory(GetDate.date);



        binding.btnBack.setOnClickListener(v -> GetHistory.getCostHistory(GetDate.getDate(-1)));
        binding.btnForword.setOnClickListener(v -> GetHistory.getCostHistory(GetDate.getDate(+1)));
        binding.expenseBtn.setOnClickListener(v -> {
            showAddCustomerDialog();
        });


        return binding.getRoot();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        binding.pgBar.setVisibility();
        AtomicBoolean firstTime = new AtomicBoolean();
        GetHistory.costHistory.observe(this, costHistories -> {
            if (firstTime.get()) {
                firstTime.set(false);
            }{
                binding.textViewDate.setText(GetDate.date);
                history = costHistories;
                calculateCost();

            }

        });
        super.onCreate(savedInstanceState);
    }






    private int shopCost =0;
    private int travCost =0;
    private int foodCost =0;
    private int otherCost =0;
    private void calculateCost() {
        shopCost =0;
        travCost=0;
        foodCost =0;
        otherCost =0;
        history.forEach(items -> {
                switch (items.getType()) {
                    case 0:
                        shopCost += items.getAmount();
                        break;
                    case 1:
                        travCost += items.getAmount();
                        break;
                    case 2:
                        foodCost += items.getAmount();
                        break;
                    case 3:
                        otherCost += items.getAmount();
                        break;
                }


        });

        binding.shoppingCost.setText(String.valueOf(shopCost));
        binding.travelCost.setText(String.valueOf(travCost));
        binding.foodCost.setText(String.valueOf(foodCost));
        binding.otherCost.setText(String.valueOf(otherCost));
        displayChart();
    }


    private void displayChart() {
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Column)
                .backgroundColor("")
                .categories(new String[]{"Shopping", "Travel", "Food", "Others"})
                .axesTextColor("#FFBB86FC")
                .dataLabelsEnabled(true)
                .yAxisGridLineWidth(0f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Cost")
                                .data(new Object[]{shopCost, travCost, foodCost,otherCost}),

                });

        binding.costGraph.aa_drawChartWithChartModel(aaChartModel);
        binding.progressBar2.setVisibility(View.GONE);
        history.clear();
    }


    DialogAddCustomerBinding dialogBinding;



    private void showAddCustomerDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        dialogBinding = DialogAddCustomerBinding.inflate(getLayoutInflater());
        bottomSheetDialog.setContentView(dialogBinding.getRoot());
        bottomSheetDialog.show();
        CostHistory costHistory = new CostHistory();

        dialogBinding.toogleBtnGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.btnShop){
                costHistory.setType(0);
            }else if (checkedId ==R.id.btnTravel){
                costHistory.setType(1);
            }else if (checkedId ==R.id.btnFood){
                costHistory.setType(2);
            }else if (checkedId ==R.id.btnOther){
                costHistory.setType(3);
            }

        });

        dialogBinding.addInfoButton.setOnClickListener(v -> {
            String amount = dialogBinding.amountTextview.getText().toString();
            if (!amount.isEmpty()) {
                costHistory.setAmount(Integer.parseInt(amount));
                Constant.CostHistory.child(GetDate.getDate(0)).child(GetDate.getHour()).setValue(costHistory);
                GetHistory.getDataToUpdate(Constant.thisMonthCost, Integer.parseInt(amount));
                bottomSheetDialog.dismiss();
            }
        });
    }










}