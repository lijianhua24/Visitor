package com.ysd.visitor.utlis;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;

public class SecondScreen extends Presentation {
    private int layout;

    public SecondScreen(Context paramContext, Display paramDisplay, int i) {
        super(paramContext, paramDisplay);
        this.layout = i;
    }


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(layout);
    }
}

