package fr.ups.sim.superpianotiles;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import fr.ups.sim.superpianotiles.util.Tile;

/**
 * Custom view that displays tiles
 */
public class TilesView extends View {

    private int tileColor = Color.BLUE;
    private int textColor = Color.WHITE;
    private Drawable mExampleDrawable;
    private float textSize = 40;
    Paint pText = new Paint();
    Paint pTile = new Paint();
    Queue<Tile> tilesVisibles = new ArrayDeque<>();
    Queue<Tile> tilesInvisibles = new ArrayDeque<>();
    Queue<Tile> allPositions = new ArrayDeque<>();

    public void setTilesVisibles(Queue<Tile> tilesVisibles) {
        this.tilesVisibles = tilesVisibles;
    }

    public void setTilesInvisibles(Queue<Tile> tilesInvisibles) {
        this.tilesInvisibles = tilesInvisibles;
    }

    public void setAllPositions(Queue<Tile> allPositions) {
        this.allPositions = allPositions;
    }

    public TilesView(Context context) {
        super(context);
        init(null, 0);
    }

    public TilesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TilesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TilesView, defStyle, 0);

        if (a.hasValue(R.styleable.TilesView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.TilesView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        pText.setTextSize(textSize);
        pText.setColor(textColor);
        pTile.setColor(tileColor);

        //Tile 1
        /*int left = 0;
        int top = getBottom() * 3 / 4;
        int right = getWidth()/5;
        int bottom = getBottom();
        addTile("1", new RectF(left, top, right, bottom), canvas);

        //Tile 2
        left = getWidth()*3/5;
        top = getBottom()- getBottom() * 3 / 4;
        right = getWidth()-getWidth()/5;
        bottom = getBottom()/2;
        addTile("2", new RectF(left, top, right, bottom), canvas);*/
        // Draw the example drawable on top of the text.
        for (Tile t:tilesVisibles){
            int left = (getWidth()/4)*t.getLeft();
            int right = left+(getWidth()/4)-1;
            int top = (getHeight()/5)*t.getTop();
            int bottom = top+ (getHeight()/5)-1;
            addTile(t.toString(),new RectF(left,top,right,bottom),canvas);
        }
        if (mExampleDrawable != null) {
            System.out.println("yes");
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    public void addTile(String order, RectF rect, Canvas canvas){
        canvas.drawRoundRect(rect, 2, 2, pTile);
        canvas.drawText(order, rect.centerX(), rect.centerY(),pText);
    }

    public void modifier(){
        tilesVisibles.poll();
        refreshDrawableState();
    }

    public boolean isPremier(float x,float y){
        int left = (int) (x/(getWidth()/4));
        int top = (int) (y/(getHeight()/5));
        Tile t = new Tile(0,top,left);
        if(tilesVisibles.isEmpty())
            return false;
        System.out.println(tilesVisibles.size());
        System.out.println(t.getTop()+" "+t.getLeft()+" "+tilesVisibles.peek().getTop()+" "+tilesVisibles.peek().getLeft());
        return t.equals(tilesVisibles.peek());
    }
       /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
