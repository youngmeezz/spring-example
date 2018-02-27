package org.springchat.repository;

/**
 * @author zaccoding
 * github : https://github.com/zacscoding
 */
public interface ChatRepository {

    public void saveMessage(String message);

    public void getMessage(int index);
}
