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
