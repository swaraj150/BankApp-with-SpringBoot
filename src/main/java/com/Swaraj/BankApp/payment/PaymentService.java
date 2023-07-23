package com.Swaraj.BankApp.payment;

import com.Swaraj.BankApp.config.JwtAuthenticationFilter;
import com.Swaraj.BankApp.transaction.Transaction;
import com.Swaraj.BankApp.transaction.TransactionRepository;
import com.Swaraj.BankApp.user.User;
import com.Swaraj.BankApp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final JwtAuthenticationFilter filter;
    public PaymentResponse makePayment(PaymentRequest request){
        Integer sender=filter.accountDetails;
        //1. reflect changes in table
        //2. solve pk issue
//        Optional<User> sender1= Optional.of(userRepository.findByAccount(sender)).orElseThrow();
        Optional<User> sender1=userRepository.findByAccount(sender);
        Optional<User> receiver1= userRepository.findByAccount(request.getReceiver());

        if(sender1.isPresent() && receiver1.isPresent()){
            User user1=sender1.get();
            User user2=receiver1.get();
            double balance1=user1.getBalance();
            double balance2=user2.getBalance();
            if(balance1<request.getAmount()){
                return PaymentResponse.builder().msg("Payment cannot be proceeded").build();
            }
            SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            var transaction= Transaction.builder()
                    .sender(sender)
                    .receiver(request.getReceiver())
                    .amount(request.getAmount())
                    .date(formatter.format(new Date()))
                    .build();
            transactionRepository.save(transaction);
            user1.setBalance(balance1-request.getAmount());
            user2.setBalance(balance2+ request.getAmount());
            userRepository.save(user1);
            userRepository.save(user2);
            return PaymentResponse.builder().msg("Payment Successful").build();
        }
        return PaymentResponse.builder().msg("User Not found").build();

    }

}
