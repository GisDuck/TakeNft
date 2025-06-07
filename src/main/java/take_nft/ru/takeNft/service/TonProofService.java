// src/main/java/take_nft/ru/takeNft/service/AuthService.java
package take_nft.ru.takeNft.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.ton.tonapi.sync.Tonapi;
import org.ton.schema.tonconnect.AccountInfoByStateInit;
import org.ton.java.tonconnect.*;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import take_nft.ru.takeNft.dto.TonProofRequest;
import take_nft.ru.takeNft.dto.TonProofRequest.AccountJson;
import take_nft.ru.takeNft.dto.TonProofRequest.ProofJson;

@Service
public class TonProofService {
    private final Map<String, Long> payloads = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();

    private static Dotenv dotenv = Dotenv.load();
    private static final long MAX_PAYLOAD_AGE = Long.parseLong(dotenv.get("MAX_PAYLOAD_AGE")); // 15 минут
    private static final String BASE_URL = dotenv.get("BASE_URL");
    private static final String TON_API_KEY = dotenv.get("TON_API_KEY");

    private static final Logger log = LoggerFactory.getLogger(TonProofService.class);

    private final Tonapi tonapi = new Tonapi(
            TON_API_KEY,
            false,
            10);

    /**
     * Сгенерировать payload для пользователя для ton proof
     */
    public String createPayload() {
        byte[] buf = new byte[16];
        random.nextBytes(buf);
        String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
        payloads.put(payload, Instant.now().getEpochSecond());
        log.info("get payload {}", payload);
        return payload;
    }

    /**
     * проверяет подпись и удаляет payload после использования
     */
    public boolean verifyUser(TonProofRequest tonProofRequest) throws Exception {
        AccountJson accountJson = tonProofRequest.accountJson();
        ProofJson proofJson = tonProofRequest.proofJson();

        log.info("verify user {}", proofJson);

        boolean isValidPayload = payloads.containsKey(proofJson.payload());
        Long createPayloadTime = payloads.remove(proofJson.payload());

        log.info("isValidPayload {}", isValidPayload);

        AccountInfoByStateInit accountInfo = tonapi
                .getTonconnect()
                .getInfoByStateInit(accountJson.walletStateInit());

        if (!isValidPayload || //проверка, что такой payload присутствует в нашей системе
                Math.abs(proofJson.timestamp() - createPayloadTime) > MAX_PAYLOAD_AGE ||
                !proofJson.domain().value().equals(BASE_URL) ||
                !accountJson.address().equals(accountInfo.getAddress())) {
            log.warn("user fatal check {}", proofJson);
            return false;
        }

        //сборка подписи
        TonProof proof = TonProof.builder()
                .timestamp(proofJson.timestamp())
                .domain(Domain.builder()
                        .value(proofJson.domain().value())
                        .lengthBytes(proofJson.domain().lengthBytes())
                        .build())
                .payload(proofJson.payload())
                .signature(proofJson.signature())
                .build();

        WalletAccount wallet = WalletAccount.builder()
                .chain(accountJson.chain())
                .address(accountJson.address())
                .publicKey(accountInfo.getPublicKey())
                .build();

//        Чекнули подпись, вернули ответ
        return TonConnect.checkProof(proof, wallet);
    }
}