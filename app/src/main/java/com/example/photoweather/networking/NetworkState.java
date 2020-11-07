package com.example.photoweather.networking;

public class NetworkState
{
    public enum State
    {
        LOADING, SUCCESS, FAILED
    }

    private State state;
    private String message;

    public NetworkState(State state, String message)
    {
        this.state = state;
        this.message = message;
    }

    public static NetworkState LOADING;
    public static NetworkState SUCCESS;
    public static NetworkState FAILED;

    static
    {
        LOADING = new NetworkState(State.LOADING, "Please wait");
        SUCCESS = new NetworkState(State.SUCCESS, "Success");
        FAILED = new NetworkState(State.FAILED, "Something went error");
    }

    public State getState()
    {
        return state;
    }

    public String getMessage()
    {
        return message;
    }
}
