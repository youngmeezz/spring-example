package org.springchat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private String sessionId;

    @Override
    public int hashCode() {
        return sessionId == null ? 0 : sessionId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Client client = (Client) o;

        if (this.sessionId == null) {
            return client.sessionId == null;
        }
        else {
            return this.sessionId.equals(client.sessionId);
        }
    }
}
