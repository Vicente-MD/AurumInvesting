package com.aurum.aurumapp.investment;

import java.util.List;

import com.aurum.aurumapp.checkingaccount.model.CheckingAccount;
import com.aurum.aurumapp.fixedincome.model.FixedIncomeModel;
import com.aurum.aurumapp.stock.model.Stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllInvestmentData {
    private double totalStock;
    private List<Stock> stock;
    private double totalCheckingAccount;
    private List<CheckingAccount> checkingAccount;
    private double totalFixedIncome;
    private List<FixedIncomeModel> fixedIncome;

    public AllInvestmentData(double totalStock, double totalCheckingAccount, double totalFixedIncome) {
        this.totalStock = totalStock;
        this.totalCheckingAccount = totalCheckingAccount;
        this.totalFixedIncome = totalFixedIncome;
    }
}
