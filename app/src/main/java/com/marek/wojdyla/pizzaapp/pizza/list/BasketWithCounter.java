package com.marek.wojdyla.pizzaapp.pizza.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.marek.wojdyla.pizzaapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BasketWithCounter extends FrameLayout {

    private TextView mCounter;

    public BasketWithCounter(@NonNull Context context) {
        super(context);
        inflate();
    }

    public BasketWithCounter(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate();
    }

    public BasketWithCounter(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate();
    }

    public BasketWithCounter(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate();
    }

    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.basket_with_counter, this, true);
        mCounter = findViewById(R.id.basketWithCounter_counter);
        mCounter.setVisibility(View.INVISIBLE);
    }

    public void setCounter(int counter) {
        if (mCounter.getVisibility() == View.INVISIBLE) {
            mCounter.setVisibility(View.VISIBLE);
            mCounter.setAlpha(0f);
            mCounter.animate().alpha(1f).start();
        }
        mCounter.setText(String.valueOf(counter));
        animate()
                .scaleY(1.2f)
                .scaleX(1.2f)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .start();
                    }
                })
                .start();
    }
}
