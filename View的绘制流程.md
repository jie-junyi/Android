View的绘制流程

##### view是什么？

###### view的继承关系

![img](https://img-blog.csdnimg.cn/20190815100748544.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTAxMjczMzI=,size_16,color_FFFFFF,t_70#pic_center)

###### Activity的组成

![img](https://img-blog.csdnimg.cn/20200927111003956.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2Nsb3VkeV9oYXBweQ==,size_16,color_FFFFFF,t_70#pic_center)

Window 类
　　该类是一个[抽象类](https://so.csdn.net/so/search?q=抽象类&spm=1001.2101.3001.7020)，提供了绘制窗口的一组通用API。每个Activity会创建一个Window用于承载View视图的显示

PhoneWindow类	

​		该[类继承](https://so.csdn.net/so/search?q=类继承&spm=1001.2101.3001.7020)于Window类，是Window类的具体实现，即我们可以通过该类具体去绘制窗口。并且，该类内部包含了一个DecorView对象，该DectorView对象是所有应用窗口(Activity界面)的根View。 简而言之，PhoneWindow类是把一个FrameLayout类即DecorView对象进行一定的包装，将它作为应用窗口的根View，并提供一组通用的窗口操作接口。

DecorView类
　　该类是PhoneWindow类的内部类。该类是一个FrameLayout的子类，并且是PhoneWindow的子类，最终会被加载到Window当中，它内部只有一个垂直方向的LinearLayout分为两部分：比如说添加TitleBar(标题栏)，以及TitleBar上的滚动条等 。最重要的一点是，它是所有应用窗口的根View 。

- TitleBar：屏幕顶部的状态栏
- ContentView：Activity对应的XML布局，通过setContentView设置到DecorView中。

##### view的绘制流程：

view的绘制流程，指的就是measure、layout和draw。其中，measure用来测量View的宽高，layout用来确定View的位置，draw则用来绘制view。

View的绘制是从ViewRootImpl的performTraversals()方法开始，从最顶层的View(ViewGroup)开始逐层对每个View进行绘制操作，下面来看一下该方法部分源代码：

###### ViewRootlmpl:

```java
private void performTraversals() {
     ...............
    //measur过程
    performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
     ...............
    //layout过程
    performLayout(lp, desiredWindowWidth, desiredWindowHeight);
     ...............
    //draw过程
    performDraw();
}
```

- measure：为测量宽高过程，如果是ViewGroup还要在onMeasure中对所有子View进行measure操作。
- layout：用于摆放View在ViewGroup中的位置，如果是ViewGroup要在onLayout方法中对所有子View进行layout操作。
- draw：往View上绘制图像。

###### Measure:

performMeasure()源码

```java
private void performMeasure(int childWidthMeasureSpec, int childHeightMeasureSpec) {
      if (mView == null) {
          return;
      }
      try {
          mView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
      } finally {
          Trace.traceEnd(Trace.TRACE_TAG_VIEW);
      }
}
```

可以看出从mView(最顶层ViewGroup)开始进行测量操作，然后逐层遍历View并执行measure操作。

###### MeasureSpec:

它是一个32位int类型数值，高两位SpacMode代表测量模式，低30位SpacSize代表测量尺寸，是View的内部类，源码如下：

```java
public class MeasureSpec {
        private static final int MODE_SHIFT = 30;  
        private static final int MODE_MASK  = 0x3 << MODE_SHIFT;  
        public static final int UNSPECIFIED = 0 << MODE_SHIFT;  
        public static final int EXACTLY = 1 << MODE_SHIFT;  
        public static final int AT_MOST = 2 << MODE_SHIFT;  
  }
```

内部包含三种测量模式：

- **UNSPECIFIED ：**父布局不会对子View做任何限制
- **EXACTLY ：**精确数值，比如使用了match_parent或者xxxdp，表示父布局已经决定了子`View`的大小，通常在这种情况下View的尺寸就是SpacSize
- **AT_MOST ：**自适应，对应wrap_content子View可以根据内容设置自己的大小，但前提是不能超出父ViewGroup的宽高。

子view的MeasureSpec由父MeasureSpec和其自己的LayoutParams(布局参数)共同决定

![img](https://img-blog.csdnimg.cn/20190908173654848.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3poYW9xaW5tdXh1ZQ==,size_16,color_FFFFFF,t_70)onMeasure()方法

```java
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       //默认宽  
       int defaultWidth = 0;    
       //默认高
       int defaultHeight = 0;    
       setMeasuredDimension(
            getDefaultSize(defaultWidth, widthMeasureSpec),  
            getDefaultSize(defaultHeight, heightMeasureSpec));  
}
```

一般的自定义View中，如果对宽高没有特殊需求可直接通过getDefaultSize()方法获取，该方法位于View中源码如下：

```java
   public static int getDefaultSize(int size, int measureSpec) {
        //默认尺寸
        int result = size;
        //获取测量模式
        int specMode = MeasureSpec.getMode(measureSpec);
        //获取尺寸
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
           case MeasureSpec.UNSPECIFIED:
               result = size;
               break;
           case MeasureSpec.AT_MOST:
           case MeasureSpec.EXACTLY:
               result = specSize;
               break;
        }
        return result;
    }
```

从源码可知，获取mode和size后会分别对三种测量模式进行判断，UNSPECIFIED使用默认尺寸，而AT_MOST和EXACTLY使用父布局给出的测量尺寸。尺寸计算完毕后通过setMeasuredDimension(width,height)设置最终宽高。

###### Layout：

performLayout()部分源码：

```java
 private void performLayout(WindowManager.LayoutParams lp, int desiredWindowWidth,
            int desiredWindowHeight) {
        .........
        final View host = mView;
        if (host == null) {
            return;
        }
        host.layout(0, 0, host.getMeasuredWidth(), host.getMeasuredHeight());
        .........
}
```

跟measure类似，同样是从mView(最顶层ViewGroup)开始进行layout操作，随后逐层遍历。layout(l,t,r,b)四个参数分别对应左上右下的位置，从而确定View在ViewGroup中的位置。下面来看一下layout()部分源码：

```java
public void layout(int l, int t, int r, int b) {
    .......
    //通过setOpticalFrame()和setFrame()老确定四个点的位置
    boolean changed = isLayoutModeOptical(mParent) ? 
    setOpticalFrame(l, t, r, b) : setFrame(l, t, r, b);
    .......
    //调用onLayout()，ViewGroup须重写此方法
    onLayout(changed, l, t, r, b);
    .......
}
```

结合源码可知layout()会将四个位置参数传递给setOpticalFrame()或者setFrame()，而setOpticalFrame()内部会调用setFrame()，所以最终通过setFrame()确定View在ViewGroup中的位置。位置确定完毕会调用onLayout(l,t,r,b)对子View进行摆放。

onLayout()

View和ViewGroup在执行完setFrame()后都会调用onLayout()方法，但上面也有提到该方法的作用是对子View进行位置摆放，所以单一View是不需要重写此方法。而ViewGroup会根据自己的特性任意对子View进行摆放。

###### Draw：

performDraw()执行后同样会从根布局开始逐层对每个View进行draw操作，在View中绘制操作时通过draw()进行，来看一下其主要源码：

```java
public void draw(Canvas canvas) {
     ........
    // 绘制背景
    drawBackground(canvas);
    // 绘制内容
    onDraw(canvas);
    // 绘制子View
    dispatchDraw(canvas);
    // 绘制装饰,如scrollBar
    onDrawForeground(canvas)
    ........
}
```

draw()方法中主要包含四部分内容，其中我们开发者只需要关心onDraw(canvas)即可，即自身的内容绘制。

- Canvas：画布，不管是文字，图形，图片都要通过画布绘制而成
- Paint：画笔，可设置颜色，粗细，大小，阴影等等等等，一般配合画布使用
- Path：路径，用于形成一些不规则图形。
- Matrix：矩阵，可实现对画布的几何变换。

### 

##### 参考博客：

1：[(50条消息) Android中View的继承关系图_cloudy_happy的博客-CSDN博客_android view的继承关系](https://blog.csdn.net/cloudy_happy/article/details/107772981)

2:[Android 面试官：简述一下 View 的绘制流程，这个都答不出来就别想拿Offer了 - 腾讯云开发者社区-腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1745688)