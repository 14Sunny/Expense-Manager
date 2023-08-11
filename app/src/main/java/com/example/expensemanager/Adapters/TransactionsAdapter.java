package com.example.expensemanager.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Models.Category;
import com.example.expensemanager.Models.Transactions;
import com.example.expensemanager.R;
import com.example.expensemanager.Views.Activities.MainActivity;
import com.example.expensemanager.databinding.RowTransactionBinding;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import io.realm.RealmResults;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>{

    Context context;
    RealmResults<Transactions> transactions;

    public TransactionsAdapter(Context context, RealmResults<Transactions> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transactions transactions1=transactions.get(position);


//        double rupee=transactions1.getAmount();
//        String rs= NumberFormat.getCurrencyInstance(new Locale("en","in")).format(rupee);
        holder.binding.transactionAmount.setText(String.valueOf(Helper.formatRupee(transactions1.getAmount())));
        holder.binding.accountLabel.setText(transactions1.getAccount());

        holder.binding.transactionDate.setText(Helper.formatDate(transactions1.getDate()));
        holder.binding.transactionCategory.setText(transactions1.getCategory());

        Category transactionCategory=Constants.getCategoryDetails(transactions1.getCategory());

        if (transactionCategory != null) {
            int categoryImage = transactionCategory.getCategoryImage();
            // Use the categoryImage value
            holder.binding.categoryIcon.setImageResource(categoryImage);
            holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(transactionCategory.getCategoryColor()));
        }else{
            holder.binding.categoryIcon.setImageResource(R.drawable.ic_other);
        }


        if(transactions1.getType().equals(Constants.INCOME)){
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.lightGreen));
        }else if(transactions1.getType().equals(Constants.EXPENSE)){
            holder.binding.transactionAmount.setTextColor(context.getColor(R.color.dark_red));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog deleteDialog=new AlertDialog.Builder(context).create();
                deleteDialog.setTitle("Delete Transaction");
                deleteDialog.setMessage("Are you sure to delete thhis transaction?");
                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialog, which) -> {
                    ((MainActivity)context).viewModel.deleteTransaction(transactions1);
                });
                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialog, which) -> {
                    deleteDialog.dismiss();
                });
                deleteDialog.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{

        RowTransactionBinding binding;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RowTransactionBinding.bind(itemView);
        }
    }
}
