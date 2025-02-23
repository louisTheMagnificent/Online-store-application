package com.project5348.bank.rpc;


import bank.Bank;
import bank.BankServiceGrpc;



import com.project5348.bank.service.BankService;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class BankServiceRPC extends BankServiceGrpc.BankServiceImplBase {

    @Autowired
    private BankService bankService;

    @Override
    @Transactional
    public void modifyBalance(Bank.ModifyBalanceRequest request, StreamObserver<Bank.ModifyBalanceResponse> responseObserver) {
        boolean success = bankService.processRefund(request.getBuyerId(), request.getSellerId(), request.getAmount());
        String message = success ? "Balance modified successfully" : "Insufficient seller balance";

        Bank.ModifyBalanceResponse response = Bank.ModifyBalanceResponse.newBuilder()
                .setSuccess(success)
                .setMessage(message)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void checkSufficientFund(Bank.CheckSufficientFundRequest request, StreamObserver<Bank.CheckSufficientFundResponse> responseObserver) {
        Long customerId = request.getCustomerId();
        Double price = request.getPrice();

        boolean isSufficient = bankService.checkSufficientFundForCustomer(customerId, price);

        Bank.CheckSufficientFundResponse response = Bank.CheckSufficientFundResponse.newBuilder()
                .setIsSufficient(isSufficient)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}


