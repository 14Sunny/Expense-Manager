package com.example.expensemanager.Views.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensemanager.Adapters.AccountsAdapter;
import com.example.expensemanager.Adapters.CategoryAdapter;
import com.example.expensemanager.Models.Account;
import com.example.expensemanager.Models.Category;
import com.example.expensemanager.Models.Transactions;
import com.example.expensemanager.R;
import com.example.expensemanager.Views.Activities.MainActivity;
import com.example.expensemanager.databinding.FragmentAddTransactionBinding;
import com.example.expensemanager.databinding.ListDialogBinding;
import com.example.expensemanager.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class addTransactionFragment extends BottomSheetDialogFragment {


    public addTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAddTransactionBinding binding;
    Transactions transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAddTransactionBinding.inflate(inflater);



        transaction=new Transactions();

        //Buttons (income/expense ) cor changes dynamically---> default to their prespective colors
        binding.incomeBtn.setOnClickListener(v -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selectors));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selectors));
            transaction.setType(Constants.INCOME);
        });
        binding.expenseBtn.setOnClickListener(v -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selectors));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.exp_selectors));
            transaction.setType(Constants.EXPENSE);
        });

//        setting date picker dailog ----> to change date of (select date) text
        binding.Date.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog=new DatePickerDialog(getContext());
             //to show the date at edit text place
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                Calendar calendar=Calendar.getInstance();
                calendar.set(calendar.DAY_OF_MONTH,view.getDayOfMonth());
                calendar.set(calendar.MONTH,view.getMonth());
                calendar.set(calendar.YEAR,view.getYear());

                //need to show date in a format ---> date Format specifying
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMM,yyyy");
                String dateToShow=dateFormat.format(calendar.getTime());
                binding.Date.setText(dateToShow);

                transaction.setDate(calendar.getTime());
                transaction.setId(calendar.getTime().getTime());
            });
            datePickerDialog.show();
        });


        //setting up recycler view for category
        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDialogBinding dialogBinding=ListDialogBinding.inflate(inflater);
                AlertDialog categoryDialog=new AlertDialog.Builder(getContext()).create();
                categoryDialog.setView(dialogBinding.getRoot());


                CategoryAdapter categoryAdapter=new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClickListner() {
                    @Override
                    public void onCategoryClicked(Category category) {
                        binding.category.setText(category.getCategoryName());
                        transaction.setCategory(category.getCategoryName());
                        categoryDialog.dismiss();
                    }
                });
                dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                dialogBinding.recyclerView.setAdapter(categoryAdapter);
                categoryDialog.show();
            }
        });


        //setting account category---> layout containing row_accounts(sample layout), accounts adapter
        binding.Account.setOnClickListener(v -> {
            ListDialogBinding dialogBinding=ListDialogBinding.inflate(inflater);
            AlertDialog accountsDialog=new AlertDialog.Builder(getContext()).create();
            accountsDialog.setView(dialogBinding.getRoot());

            ArrayList<Account> accounts=new ArrayList<>();
            accounts.add(new Account(0,"Cash"));
            accounts.add(new Account(0,"Internet Banking"));
            accounts.add(new Account(0,"Paytm"));
            accounts.add(new Account(0,"Gpay"));
            accounts.add(new Account(0,"Others"));

//            AccountsAdapter adapter=new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccountsClickListner() {
//                @Override
//                public void onAccountSelected(Account account) {
//                    binding.Account.setText(account.getAccountName());
//                    accountsDialog.dismiss();
//                }
//            });
            AccountsAdapter adapter=new AccountsAdapter(getContext(), accounts, new AccountsAdapter.AccountsClickListner() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.Account.setText(account.getAccountName());
                    transaction.setAccount(account.getAccountName());
                    accountsDialog.dismiss();
                }
            });


            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
            dialogBinding.recyclerView.setAdapter(adapter);
            accountsDialog.show();
        });

        binding.saveTransactionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount=Double.parseDouble(binding.Amount.getText().toString());
                String note= binding.Notes.getText().toString();


                if(transaction.getType().equals(Constants.EXPENSE)){
                    transaction.setAmount(amount*-1);
                }else{
                    transaction.setAmount(amount);
                }

                transaction.setNote(note);
                ((MainActivity)getActivity()).viewModel.addTransactions(transaction);
                ((MainActivity)getActivity()).getTransaction();
                dismiss();
            }
        });
        return binding.getRoot();
    }
}