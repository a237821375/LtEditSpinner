Android 代码模块
组件化实现

第一步
复制lteditspinner组件到根目录


第二步
xxx/build.gradle里加入     api project(':lteditspinner')


第三步
settings.gradle 里加入 “, ':lteditspinner'”


第四步
代码调用test
  java
        String[] data = { "Apple", "Banana", "Orange", "Watermelon",
                "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango" };
            
        List<DataTest> list = new ArrayList<DataTest>();
        for (int i=0; i<data.length; i++)
        {
            list.add(new DataTest(i, data[i]));
        }

        LTEditSpinner<DataTest> ltEditSpinner = (LTEditSpinner)findViewById(R.id.edit_spinner);
        ltEditSpinner.initData(list, new LTEditSpinner.OnESItemClickListener<DataTest>() {
            @Override
            public void onItemClick(DataTest item) {
                Toast.makeText(MainActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        
 资源文件   
 
     <cn.lognteng.editspinner.lteditspinner.LTEditSpinner
        android:id="@+id/edit_spinner"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginTop="114dp"></cn.lognteng.editspinner.lteditspinner.LTEditSpinner>
