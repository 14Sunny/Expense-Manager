package com.example.expensemanager.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.expensemanager.Adapters.TransactionsAdapter;
import com.example.expensemanager.Models.Transactions;
import com.example.expensemanager.R;
import com.example.expensemanager.ViewModels.MainViewModel;
import com.example.expensemanager.Views.Fragments.addTransactionFragment;
import com.example.expensemanager.Adapters.viewPagerDialogAdapter;
import com.example.expensemanager.databinding.ActivityMainBinding;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    TabLayout tabLayout;
    ViewPager view;

    int selectedTab=0;//0== daily, 1==monthly, 2==calender,3==notes
    public MainViewModel viewModel;

    Calendar calender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //view models initialize
        viewModel=new ViewModelProvider(this).get(MainViewModel.class);


        // setting up of tablayout and view pager
        tabLayout=findViewById(R.id.tabLayout);
        view=findViewById(R.id.viewPager);

        //view pager setup for tablayout
        viewPagerDialogAdapter adapter=new viewPagerDialogAdapter(getSupportFragmentManager());
        view.setAdapter(adapter);
        tabLayout.setupWithViewPager(view);

        //setting up the tool/action bar
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Transactions");


        //setting up the calender of home screen
        calender=Calendar.getInstance();
        updateDate();

       binding.nextDateBtn.setOnClickListener(v -> {
           calender.add(Calendar.DATE,1);
           updateDate();
       });
        binding.prevDateBtn.setOnClickListener(v -> {
            calender.add(Calendar.DATE,-1);
            updateDate();
        });


        //setting up the tablayout features

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//
//                Toast.makeText(MainActivity.this,tab.getText().toString(), Toast.LENGTH_SHORT).show();
                if(tab.getText().equals("Monthly")){
                    selectedTab=1;
                    updateDate();
                }else if(tab.getText().equals("Daily")){
                    selectedTab=0;
                    updateDate();
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //Floating Action Button setup ----> bottom sheet opener(new layout)
        binding.floatingActionButton.setOnClickListener(v ->
                new addTransactionFragment().show(getSupportFragmentManager(),null));


        //setting a list for recycler view on our hone screen app
        Constants.setCategories();

        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));
        viewModel.transations.observe(this, new Observer<RealmResults<Transactions>>() {
            @Override
            public void onChanged(RealmResults<Transactions> transactions) {
                TransactionsAdapter transactionsAdapter=new TransactionsAdapter(MainActivity.this,transactions);
                binding.transactionsList.setAdapter(transactionsAdapter);
                if(transactions.size()>0){
                    binding.emptyState.setVisibility(View.GONE);
                }else{
                    binding.emptyState.setVisibility(View.VISIBLE);
                }


            }
        });
        viewModel.getTotalIncome.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalLabel.setText(String.valueOf(Helper.formatRupee(aDouble)));
            }
        });

        viewModel.totalExpense.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenseLabel.setText(String.valueOf(Helper.formatRupee(aDouble)));

            }
        });

        viewModel.totalIncome.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomeLabel.setText(String.valueOf(Helper.formatRupee(aDouble)));
            }
        });
        viewModel.getTransactions(calender);
//


    }

    public void getTransaction(){
        viewModel.getTransactions(calender);
    }

    void updateDate(){
        if(selectedTab==0){
            binding.currDate.setText(Helper.formatDate(calender.getTime()));
        }else{
            binding.currDate.setText(Helper.formatByMonth(calender.getTime()));
        }
        viewModel.getTransactions(calender);
    }

    // realm initialize


    //Top menu settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}