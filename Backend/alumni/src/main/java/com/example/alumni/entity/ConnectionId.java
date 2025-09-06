// package com.example.alumni.entity;

// import lombok.EqualsAndHashCode;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import java.io.Serializable;

// @NoArgsConstructor
// @Getter
// @Setter
// @EqualsAndHashCode
// public class ConnectionId implements Serializable {
//     private String requesterId;
//     private String approverId;
// }
package com.example.alumni.entity;

import java.io.Serializable;
import java.util.Objects;

public class ConnectionId implements Serializable {

    private String requesterId;
    private String approverId;

    // Default constructor is required
    public ConnectionId() {}

    public ConnectionId(String requesterId, String approverId) {
        this.requesterId = requesterId;
        this.approverId = approverId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectionId)) return false;
        ConnectionId that = (ConnectionId) o;
        return Objects.equals(requesterId, that.requesterId) &&
               Objects.equals(approverId, that.approverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requesterId, approverId);
    }
}
