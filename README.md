Screenshots
===========
![](https://github.com/yeray697/DiaryCalendarView/blob/master/resources/DiaryCalendarView.gif)
![](https://github.com/yeray697/DiaryCalendarView/blob/master/resources/DiaryCalendarView.png)

How do I use it?
=========
## Setup

#### Dependencies

##### 1. Get the AAR file
You can clone or download the project, modify it (or not), and go to Build -> Make project (Ctrl + F9). You will find the .AAR file at your/projects/path/diarycalendarview/build/outputs/aar
Also you can download the last version from https://github.com/yeray697/DiaryCalendarView/tree/master/AAR%20files%20versions

##### 2. Add the library

File -> New -> New module -> Import .JAR/.AAR Package -> Click at select package (you can choose a name for the library added) -> Finish

##### 3. Add dependencies

Add to your project's gradle (change 'DiaryCalendarView' if you changed the name before)

```
dependencies {
    compile project(":DiaryCalendarView")
    compile 'com.android.support:recyclerview-v7:25.1.0'
    compile 'com.prolificinteractive:material-calendarview:1.4.2'
}
```
More info at: https://developer.android.com/studio/projects/android-library.html#AddDependency

### Functions
```
public void addEvent(List<? extends DiaryCalendarEvent> newEvents)
public void addEvent(DiaryCalendarEvent newEvent)
public void setCalendar(MaterialCalendarView calendar)
public MaterialCalendarView getCalendar()
public Toolbar getToolbar()
public int getEventColor()
public void setEventColor(int eventColor)
public int getEventRadius()
public void setEventRadius(int eventRadius)
public void clearEvents()
public ArrayList<DiaryCalendarEvent> getEvents()
public DiaryCalendarEvent getEventAtPosition(int position)
public void remove(DiaryCalendarEvent event)
```

### Customization
You can only customize the attributes with the methods in the previous section
But you can override the colors and dimensions used
```
    <dimen name="radio_calendar_day">12dp</dimen>
    <dimen name="border_day_size">1dp</dimen>
```
```
    <color name="selected_day">#4484d2</color>
    <color name="calendar_background">#6db6f1</color>
    <color name="current_day" >#FF4081</color>
    <color name="background_item_not_expanded">#7124f9</color>
    <color name="background_item_expanded">#9663ef</color>
    <color name="selected_day_diary">#33ff00</color>
    <color name="current_event">#FF4081</color>
```
If you want to further customize it, use
```
public void setCalendar(MaterialCalendarView calendar)
public MaterialCalendarView getCalendar()
```
(more info about MaterialCalendarView class [here](https://github.com/prolificinteractive/material-calendarview/)
or create your own version of this library



MIT LICENSE
===========
Copyright (c) 2017 Yeray Ruiz Juárez

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
