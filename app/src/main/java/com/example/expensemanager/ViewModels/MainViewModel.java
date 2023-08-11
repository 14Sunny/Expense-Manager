package com.example.expensemanager.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.expensemanager.Models.Transactions;
import com.example.expensemanager.utils.Constants;

import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainViewModel extends AndroidViewModel {

    public MutableLiveData<RealmResults<Transactions>> transations=new MutableLiveData<>();
    public MutableLiveData<Double> totalIncome=new MutableLiveData<>();
    public MutableLiveData<Double> getTotalIncome=new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense=new MutableLiveData<>();
    Realm realm;
    Calendar calendar;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Realm.init(application);
        setupDatabase();
    }

    public void getTransactions(Calendar calendar){
        this.calendar=calendar;
        //now fetch data from realm
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);


        double income=realm.where(Transactions.class)
//                .equalTo("date",calendar.getTime()).findAll();
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                .equalTo("type", Constants.INCOME)
                .sum("amount").doubleValue();


        double expense=realm.where(Transactions.class)
//                .equalTo("date",calendar.getTime()).findAll();
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                .equalTo("type", Constants.EXPENSE)
                .sum("amount").doubleValue();


        double total=realm.where(Transactions.class)
//                .equalTo("date",calendar.getTime()).findAll();
                .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                .sum("amount").doubleValue();

        totalIncome.setValue(income);
        totalExpense.setValue(expense);
        getTotalIncome.setValue(total);

        RealmResults<Transactions> newTransactions=realm.where(Transactions.class)
//                .equalTo("date",calendar.getTime()).findAll();
        .greaterThanOrEqualTo("date",calendar.getTime())
                .lessThan("date",new Date(calendar.getTime().getTime()+(24*60*60*1000)))
                        .findAll();
        transations.setValue(newTransactions);
    }


    public void deleteTransaction(Transactions transaction){
        realm.beginTransaction();
        transaction.deleteFromRealm();
        realm.commitTransaction();
        getTransactions(calendar);
    }
    public void addTransactions(Transactions transaction){
        realm.beginTransaction();
        // all realm code comes uns=der begin and commit transaction
        realm.copyToRealmOrUpdate(transaction);

        realm.commitTransaction();
    }

    public void addTransactions(){
        realm.beginTransaction();
        // all realm code comes uns=der begin and commit transaction
        realm.copyToRealmOrUpdate(new Transactions("Income","Salary","Cash","Some notes here",new Date(),5000,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transactions("Income","Rent","Paytm","Some notes here",new Date(),5235,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transactions("Expense","Food","Gpay","Some notes here",new Date(),525460,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transactions("Income","Shopping","Cash","Some notes here",new Date(),8610,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transactions("Expense","Travel","Bank","Some notes here",new Date(),5480,new Date().getTime()));
        realm.copyToRealmOrUpdate(new Transactions("Expense","Business","Other","Some notes here",new Date(),500,new Date().getTime()));
        realm.commitTransaction();
    }
    void setupDatabase(){

        realm=Realm.getDefaultInstance();   //instance creation
    }
}
