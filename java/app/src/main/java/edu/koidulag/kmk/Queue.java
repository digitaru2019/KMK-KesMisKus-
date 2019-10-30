package edu.koidulag.kmk;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Queue {

    private static Queue _i;
    private RequestQueue rq;
    private static Context c;

    private Queue(Context _c) {
        c = _c;
        rq = getRequestQueue();
    }

    public static synchronized Queue i(Context c) {
        if (_i == null) {
            _i = new Queue(c);
        }
        return _i;
    }

    private RequestQueue getRequestQueue() {
        if (rq == null) {
            rq = Volley.newRequestQueue(c.getApplicationContext());
        }
        return rq;
    }

    public <T> void add(Request<T> r) {
        getRequestQueue().add(r);
    }
}
