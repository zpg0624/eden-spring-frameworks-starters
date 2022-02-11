package com.eden.framework.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author eden
 * @since 2020/8/14
 */
public class AccessTypeHeaderMapper {

    private final Map<AccessType, String> accessTypeHeaderMappings = new HashMap<>();

    public AccessTypeHeaderMapper mapping(AccessType accessType, String header) {
        accessTypeHeaderMappings.put(accessType, header);
        return this;
    }

    public Map<AccessType, String> getMappings() {
        return accessTypeHeaderMappings;
    }
}
