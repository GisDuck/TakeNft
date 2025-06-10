package take_nft.ru.takeNft.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.ton.schema.nft.NftItem;
import org.ton.schema.tonconnect.AccountInfoByStateInit;
import org.ton.tonapi.sync.Tonapi;
import take_nft.ru.takeNft.dto.TonProofRequest;

import java.util.List;

@Service
public class TonApiService {

    private static Dotenv dotenv = Dotenv.load();
    private static final String TON_API_KEY = dotenv.get("TON_API_KEY");

    private final Tonapi tonapi = new Tonapi(
            TON_API_KEY,
            false,
            10);

    public AccountInfoByStateInit getAccountInfoByState(String walletStateInit) {
        return tonapi
                .getTonconnect()
                .getInfoByStateInit(walletStateInit);
    }
}
