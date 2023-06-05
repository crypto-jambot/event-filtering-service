package dev.rabbit.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@NoArgsConstructor
public class QueueService {
    private static final Map<String, Long> queuedAddresses = new ConcurrentHashMap<>();

    public boolean checkIfInQueue(String address) {
        Long currentTime = System.currentTimeMillis();
        if (queuedAddresses.containsKey(address) &&
                (currentTime - queuedAddresses.get(address)) >= 5000L) {
            queuedAddresses.put(address, currentTime);
            return false;
        }
        else if (queuedAddresses.containsKey(address)) {
            return true;
        }
        queuedAddresses.put(address, currentTime);
        return false;
    }


}
