package com.cector.algorithm;

import java.util.*;

public class BoxPacking {
    public static void main(String[] args){
        //箱子的规格列表，分别为长、宽、高和支撑的最大层数
        int[][] boxPool  ={
                {670, 560, 75, 4},
                {900, 560, 75, 4},
                {800, 330, 75, 4},
                {670, 450, 100, 5},
                {800, 450, 100, 5}};
        //分别对应板材的长宽高和当前板材是否应被处理：0 未被处理， 1 已经被处理
        int[][] panelList = {{635, 448, 18, 0},
                             {635, 448, 18, 0},
                             {46, 598, 19, 0},
                             {160, 998, 18, 0},
                             {471, 498, 18, 0},
                             {471, 498, 18, 0},
                             {240, 598, 18, 0},
                             {788, 398, 18, 0},
                             {788, 398, 18, 0},
                             {788, 323, 18, 0},
                             {788, 323, 18, 0},
                             {666, 148, 18, 0},
                             {666, 148, 18, 0},
                             {666, 148, 18, 0},
                             {804, 100, 18, 0},
                             {654, 100, 18, 0},
                             {340, 100, 18, 0},
                             {340, 100, 18, 0},
                             {340, 100, 18, 0},
                             {340, 100, 18, 0},
                             {670, 560, 18, 0},
                             {800, 330, 18, 0},
                             {800, 330, 18, 0},
                             {800, 330, 18, 0},
                             {800, 330, 18, 0}};
        //要求将箱子放到最合适的箱子汇总，使得箱子的总个数最少
        preTreatment(panelList, boxPool);
        Map<Box, List<Plate>> res = new HashMap<>();
        //最适合的箱子标准：长宽刚好对应到箱子，不会晃动；
        for(int[] panel: panelList){
            //每个面板遍历盒子，找到刚好装下自己的盒子
            for(int[] box: boxPool){
                if(panel[0] == box[0] || panel[1] == box[1]){
                    Box tmp = new Box(box[0], box[1], box[2], box[3]);
                    if(res.containsKey(tmp)){
                        List<Plate> tmpPlate = res.get(tmp);
                        tmpPlate.add(new Plate(panel[0], panel[1], panel[2]));
                        res.put(tmp, tmpPlate);
                    }else{
                        List<Plate> tmpPlate = new ArrayList<Plate>();
                        tmpPlate.add(new Plate(panel[0], panel[1], panel[2]));
                        res.put(tmp, tmpPlate);
                    }
                    //该面板已经装箱
                    panel[3] = 1;
                }
            }
        }
        //todo(根据当前装好的箱子，按照满箱的标砖选择面板)
    }

    /**
     * 数据预处理：将板件的长宽预处理，满足长不小于宽,并对数据进行逆序处理，长度相同的，宽度逆序
     */
    private static void preTreatment(int[][] panelList, int[][] boxPool){
        for(int[] panel: panelList){
            assert panel.length >= 2;
            if(panel[0] < panel[1]){
                swapValue(panel);
            }
        }
        reverseOrder(panelList);
        reverseOrder(boxPool);
    }

    private static void swapValue(int[] panel){
        int tmp = panel[0];
        panel[0] = panel[1];
        panel[1] = tmp;
    }

    private static void reverseOrder(int[][] input){
        Arrays.sort(input, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2)
            {
                if(o1[0] != o2[0]){
                    return o2[0] - o1[0];
                }else{
                    return o2[1] - o1[1];
                }
            }
        });
    }
}

class Box{
    int Length;
    int Width;
    int Height;
    int Floor;

    public Box(int Length, int Width, int Heigth, int Floor){
        this.Length = Length;
        this.Width = Width;
        this.Height = Heigth;
        this.Floor = Floor;
    }

    //对象比较要重写equals方法和hashcode，避免map中内存泄漏
    @Override
    public boolean equals(Object o){
        //自反性
        if(this == o){
            return true;
        }
        //同一类型
        if(!(o instanceof Box)){
            return false;
        }
        //强制类型转换
        Box box = (Box) o;
        return this.Height == box.Height &&
                this.Width == box.Width &&
                this.Length == box.Length
                && this.Floor == box.Floor;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.Height, this.Width, this.Length, this.Floor);
    }
}

class Plate{
    int Length;
    int Width;
    int Heigth;

    public Plate(int Length, int Width, int Heigth){
        this.Length = Length;
        this.Width = Width;
        this.Heigth = Heigth;
    }
}


