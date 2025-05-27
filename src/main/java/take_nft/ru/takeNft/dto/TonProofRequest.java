// src/main/java/take_nft/ru/takeNft/dto/ProofRequest.java
package take_nft.ru.takeNft.dto;

public record TonProofRequest(
        AccountJson accountJson,
        ProofJson proofJson
) {
        public record AccountJson(
                String address,
                int chain,
                String walletStateInit
        ) {}
        public record ProofJson(
                long timestamp,
                Domain domain,
                String payload,
                String signature
        ) {}
        public record Domain(
                int lengthBytes,
                String value
        ) {}
}
