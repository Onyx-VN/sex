package com.ff.cheat

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View

class OverlayPanel(ctx: Context) : View(ctx) {
    private var vis=true; private var drag=false; private val txt=Paint().apply{ color=Color.WHITE; textSize=24f; isAntiAlias=true }; private val bg=Paint().apply{ color=Color.argb(200,0,0,0) }; private val slide=RectF()

    override fun onDraw(c:Canvas){
        if(!vis)return; c.drawRect(10f,10f,500f,400f,bg); var y=60f
        drawSwitch(c,20f,y,"ESP",AimbotEngine.espOn); y+=50f; drawSwitch(c,20f,y,"BONE",AimbotEngine.boneOn); y+=50f; drawSwitch(c,20f,y,"FOV",AimbotEngine.fovOn); y+=50f; drawSwitch(c,20f,y,"AIM",AimbotEngine.aimOn); y+=70f
        c.drawText("FOV: ${AimbotEngine.fovRad.toInt()}px",20f,y,txt); y+=35f; slide.set(20f,y,480f,y+30f); c.drawRect(slide,Paint().apply{color=Color.GRAY})
        val thumb=20f+(AimbotEngine.fovRad/400f)*460f; c.drawCircle(thumb,y+15f,20f,Paint().apply{color=Color.CYAN}); c.drawRect(20f,y,20f+(AimbotEngine.fovRad/400f)*460f,y+30f,Paint().apply{color=Color.parseColor("#33B5E5")})
        y+=60f; c.drawRect(20f,y,150f,y+40f,Paint().apply{color=Color.RED}); c.drawText("HIDE",60f,y+28f,txt)
    }

    private fun drawSwitch(c:Canvas,x:Float,y:Float,l:String,e:Boolean){ c.drawText(l,x,y+25f,txt); c.drawRect(x+200f,y,x+300f,y+40f,Paint().apply{color=if(e) Color.GREEN else Color.RED}); c.drawCircle(x+250f,y+20f,15f,Paint().apply{color=Color.WHITE}) }

    override fun onTouchEvent(e:MotionEvent):Boolean{ val x=e.x; val y=e.y
        when(e.action){
            MotionEvent.ACTION_DOWN->{
                if(x in 220f..520f && y in 60f..100f){ AimbotEngine.espOn=!AimbotEngine.espOn; invalidate(); return true }
                if(x in 220f..520f && y in 110f..150f){ AimbotEngine.boneOn=!AimbotEngine.boneOn; invalidate(); return true }
                if(x in 220f..520f && y in 160f..200f){ AimbotEngine.fovOn=!AimbotEngine.fovOn; invalidate(); return true }
                if(x in 220f..520f && y in 210f..250f){ AimbotEngine.aimOn=!AimbotEngine.aimOn; invalidate(); return true }
                if(slide.contains(x,y)){ drag=true; setVal(x); invalidate(); return true }
                if(x in 20f..150f && y in 310f..350f){ vis=false; invalidate(); return true }
            }
            MotionEvent.ACTION_MOVE->{ if(drag){ setVal(x); invalidate(); return true } }
            MotionEvent.ACTION_UP->{ drag=false }
        }; return super.onTouchEvent(e) }

    private fun setVal(x:Float){ var nx=x.coerceIn(20f,480f); var v=((nx-20f)/460f)*400f; AimbotEngine.fovRad=v.coerceIn(50f,400f) }

    fun show(){ vis=true; invalidate() }
}
