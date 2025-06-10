package take_nft.ru.takeNft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RedisMapStore {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void put(String mapName, String key, Object value) {
        redisTemplate.opsForHash().put(mapName, key, value);
    }

    public Object get(String mapName, String key) {
        Object value = redisTemplate.opsForHash().get(mapName, key);
        return value != null ? value.toString() : null;
    }

    public void remove(String mapName, String key) {
        redisTemplate.opsForHash().delete(mapName, key);
    }

    public boolean hasKey(String mapName, String key) {
        return redisTemplate.opsForHash().hasKey(mapName, key);
    }

    public Map<Object, Object> getAll(String mapName) {
        return redisTemplate.opsForHash().entries(mapName);
    }
}
