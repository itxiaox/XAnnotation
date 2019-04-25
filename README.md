

# 自定义注解框架 XAnnotation
[ ![Download](https://api.bintray.com/packages/itxiaox/maven/annotation/images/download.svg) ](https://bintray.com/itxiaox/maven/annotation/_latestVersion)

## 1. 功能介绍
自定义注解框架，实现setConentView, findViewById, setOnClickListener等功能，是基于反射实现的运行时注解框架

## 2. 使用方法
```
allprojects {
    repositories {
        jcenter()
        maven {
            url 'https://dl.bintray.com/itxiaox/maven/'
        }
    }
}
```
在module 中添加依赖
```
compile 'com.itxiaox:annotation:1.0.0'
```

* 代码示例
```
 @ContentView(R.layout.activity_main) 
 public class MainActivity extends BaseActivity { 
 
	 @InjectView(R.id.tv_info) 
	 private TextView textView; 
 
 @Override 
 protected void onCreate(Bundle savedInstanceState) { 
	 super.onCreate(savedInstanceState); 
	 textView.setText("测试"); 
 } 
 
 @OnClick({R.id.tv_info,R.id.btn_submit}) 
 public void showInfo(View view){ 
 Toast.makeText(MainActivity.this, "自定义注解OnClick", Toast.LENGTH_SHORT).show(); 
 } 
 @OnLongClick({R.id.tv_info}) 
 public void showLongInfo(View view){ 
	 Toast.makeText(MainActivity.this, "自定义注解OnLongClick",   Toast.LENGTH_SHORT).show(); 
	 } 
 } 
```
# 3. 实现思路
	核心思路原理，反射，注解，泛型的运用


* 参考资料
 [网易云课堂·揭秘IOC注入框架，实现RecyclerView条目点击](https://study.163.com/course/courseLearn.htm?courseId=1209230809#/learn/live?lessonId=1278871528&courseId=1209230809)

# LICENSE

	Copyright 2018 Xiaox

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
