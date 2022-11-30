package com.aurum.aurumapp.investment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurum.aurumapp.checkingaccount.repository.CheckingAccountRepository;
import com.aurum.aurumapp.fixedincome.model.FixedIncomeModel;
import com.aurum.aurumapp.fixedincome.repository.FixedIncomeRepository;
import com.aurum.aurumapp.stock.model.Stock;
import com.aurum.aurumapp.stock.repository.StockRepository;
import com.aurum.aurumapp.transaction.repository.TransactionRepository;
import com.aurum.aurumapp.treasurydirect.repository.TreasuryDirectRepository;
import com.aurum.aurumapp.wallet.model.Wallet;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/investment")
@RequiredArgsConstructor
public class InvestmentHistoricalDataController {
    private final TransactionRepository transactionRepository;
    private final DataTreatment dataTreatment;
    private final StockRepository stockRepository;
    private final FixedIncomeRepository fixedIncomeRepository;
    private final TreasuryDirectRepository treasuryDirectRepository;
    private final CheckingAccountRepository checkingAccountRepository;

    @GetMapping("/history")
    public ResponseEntity<?> getInvestmentHitory(@RequestBody Wallet wallet) {
        // pega as transações com esse id de carteira
        var transactions = transactionRepository.findByWallet(wallet);
        if (!transactions.isEmpty()) {
            // array que estará com o histórico de investimentos
            List<InvestData> history = new ArrayList<>();
            // para cada transação efetua um tipo de cálculo diferente, iterando no array
            // investData
            transactions.get().forEach(t -> {
                Long id = Long.parseLong(Long.toString(t.getId()).substring(1));
                if (t.getInvestmentType().getInvestmentType().equals("STOCK")) {
                    Stock stock = stockRepository.findById(id).get();
                    // entra no cálculo de ações, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    temporary.addAll(history);
                    history.clear();
                    history.addAll(dataTreatment.stockHist(stock, temporary));
                } else if (t.getInvestmentType().getInvestmentType().equals("FIXED_INCOME")) {
                    FixedIncomeModel fixedIncome = fixedIncomeRepository.findById(id).get();
                    // entra no cálculo de renda fixa, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    System.out.println(history);
                    if (!history.isEmpty()) {
                        temporary.addAll(history);
                        history.clear();
                        history.addAll(dataTreatment.fixedIncomeHist(fixedIncome, temporary));
                    } else {
                        history.addAll(dataTreatment.fixedIncomeHist(fixedIncome, history));
                    }
                } else if (t.getInvestmentType().getInvestmentType().equals("TREASURY_DIRECT")) {
                    var treasuryDirect = treasuryDirectRepository.findById(id).get();
                    // entra no cálculo de tesouro direto, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    temporary.addAll(history);
                    history.clear();
                    history.addAll(dataTreatment.treasuryDirectHist(treasuryDirect, temporary));
                } else if (t.getInvestmentType().getInvestmentType().equals("CHECKING_ACCOUNT")) {
                    var checAccount = checkingAccountRepository.findById(id).get();
                    // entra no cálculo de conta corrente, retornando um array disso
                    List<InvestData> temporary = new ArrayList<>();
                    temporary.addAll(history);
                    history.clear();
                    history.addAll(dataTreatment.checkingAccountHist(checAccount, temporary));
                }
            });

            System.out.println(history);

            return ResponseEntity.ok(history);
        }
        return ResponseEntity.ok().build();
    }
}
