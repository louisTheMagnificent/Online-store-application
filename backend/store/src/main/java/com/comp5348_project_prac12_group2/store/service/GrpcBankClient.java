package com.comp5348_project_prac12_group2.store.service;

import bank.Bank;
import bank.BankServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcBankClient {

    private final BankServiceGrpc.BankServiceBlockingStub bankServiceStub;

    public GrpcBankClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();
        this.bankServiceStub = BankServiceGrpc.newBlockingStub(channel);
    }


    public boolean modifyBalance(Long buyerId, Long sellerId, double amount) {
        Bank.ModifyBalanceRequest request = Bank.ModifyBalanceRequest.newBuilder()
                .setBuyerId(buyerId)
                .setSellerId(sellerId)
                .setAmount(amount)
                .build();

        Bank.ModifyBalanceResponse response = bankServiceStub.modifyBalance(request);

        return response.getSuccess();
    }

    public boolean checkSufficientFund(Long customerId, double amount){
        Bank.CheckSufficientFundRequest request = Bank.CheckSufficientFundRequest.newBuilder()
                .setCustomerId(customerId)
                .setPrice(amount)
                .build();

        Bank.CheckSufficientFundResponse response = bankServiceStub.checkSufficientFund(request);
        return response.getIsSufficient();
    }


}
