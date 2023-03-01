package ru.kaznacheev.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kaznacheev.entity.Wallet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Wallet_information")
public class WalletInfoDto {

    private static final String WALLET_NAME = "_wallet";

    @JsonValue
    private Map<String, String> info;

    public WalletInfoDto(List<Wallet> wallets) {
        info = new HashMap<>();
        StringBuilder walletTitle;
        String walletBalance;
        for (Wallet wallet : wallets) {
            walletTitle = new StringBuilder(wallet.getCurrency().getTitle()).append(WALLET_NAME);
            walletBalance = wallet.getAmount().stripTrailingZeros().toPlainString();
            info.put(walletTitle.toString(), walletBalance);
        }
    }

    public WalletInfoDto(Wallet wallet) {
        info = new HashMap<>();
        String walletTitle = wallet.getCurrency().getTitle() + WALLET_NAME;
        String walletBalance = wallet.getAmount().stripTrailingZeros().toPlainString();
        info.put(walletTitle, walletBalance);
    }
    
}
