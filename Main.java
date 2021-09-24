import java.util.*;
import java.util.stream.Collectors;

public class Main
{
    public static void main(String[] args)
    {
	System.out.println("グラフ作成テスト");
	
	List<Node> nodes = new ArrayList<Node>();
	nodes.add(new Node("a",new String[]{"b","f"}));
	nodes.add(new Node("b",new String[]{"d"}));
	nodes.add(new Node("c",new String[]{"g"}));
	nodes.add(new Node("d",new String[]{}));
	nodes.add(new Node("e",new String[]{"d","g"}));
	nodes.add(new Node("f",new String[]{"b","d"}));
	nodes.add(new Node("g",new String[]{"b"}));
	Graph graph = new Graph(nodes);

	graph.Print_graph_status();
	
	int[] test = Agostino_algorithm(graph);

	System.out.print("Agostinoアルゴリズムでのエンコード結果：");
	System.out.println(Arrays.toString(test));
	
    }


    public static int[] Agostino_algorithm(Graph graph)
    {
	int max_priority = graph.nodes.size();
	List<Integer> return_list = new ArrayList<>();
	int height = 0;
	while(height < 100)
	    {
		int[] block = graph.Block_of_height(height);
		while(block.length > 0)
		    {
			if(block.length > 1)
			    {
				ArrayList<int[]> sets = new ArrayList<>();
				// setの集合を導出
				for(int i=0;i<block.length;i++)
				    {
					sets.add(graph.nodes.get(block[i]).arrows_id);
					//System.out.println(Arrays.toString(graph.nodes.get(block[i]).arrows_id));
				    }
				    
				// 各setの最低優先度を導出
				int[] min_prioritys = new int[sets.size()];
				for(int i=0;i<sets.size();i++)
				    {
					// setの要素を優先度に変換
					int[] prioritys_of_set = new int[sets.get(i).length];
					for(int j=0;j<prioritys_of_set.length;j++)
					    prioritys_of_set[j] = Ret_priority(return_list.stream().mapToInt(x->x).toArray(),max_priority,sets.get(i)[j]);
					//System.out.println(Arrays.toString(prioritys_of_set));
					sets.set(i,prioritys_of_set);
				    }
				    
				//System.out.println(Arrays.toString(min_prioritys));


				while(true)
				{
				    
				    for(int i=0;i<min_prioritys.length;i++)
					min_prioritys[i] = Min(sets.get(i));
				    // 最大の最低優先度を導出
				    int max_of_min_prioritys = Max_index(min_prioritys);
				    //System.out.println(Arrays.toString(min_prioritys));

				    //System.out.println(max_of_min_prioritys);
				    // 最大が2つ以上あったら
				    if(max_of_min_prioritys == -2)
					{
					    // 最大優先度を削除
					    int max_priority_in_sets = Max(min_prioritys);
					    for(int i=0;i<sets.size();i++)
						{
						    for(int j=0;j<sets.get(i).length;j++)
							if(sets.get(i)[j] == max_priority_in_sets)
								sets.set(i,Remove(sets.get(i),j));
						    //System.out.println(Arrays.toString(sets.get(i)));
						}
					}
				    else // 最大が1つに決定したら
					{
					    // そのノードを比較ブロックから削除して出力に追加
					    return_list.add(block[max_of_min_prioritys]);
					    block = Remove(block,max_of_min_prioritys);
					    /*System.out.print("RENEWED BLOCK = ");
					    System.out.println(Arrays.toString(block));
					    System.out.print("RETURN ;");
					    System.out.println(Arrays.toString(return_list.stream().mapToInt(x->x).toArray()));
					    */
					    min_prioritys = Remove(min_prioritys,max_of_min_prioritys);
					}
				    
				    
				    if(block.length == 0)
					break;
				}
				
			    }
			else
			    {
				return_list.add(block[0]);
				//System.out.print("RETURN ;");
				//System.out.println(Arrays.toString(return_list.stream().mapToInt(x->x).toArray()));
				break;
			    }
		    }
		height++;
	    }
	
	return return_list.stream().mapToInt(x->x).toArray();
    }

    public static int Ret_priority(int[] array,int max_prioritys,int node_id)
    {
	int priority = max_prioritys;
	
	for(int i=0;i<array.length;i++)
	    {
		if(array[i] == node_id)
		    return priority;
		priority -= 1;
	    }

	return -1;
    }

    public static int[] Remove(int[] array,int index)
    {
	int[] renewed = new int[array.length-1];
	int j=0;
	for(int i=0;i<array.length;i++)
	    {
		if(!(i == index))
		    {
			renewed[j] = array[i];
			j++;
		    }
	    }
	return renewed;
    }
    
    

    public static int Max_index(int[] array)
    {
	int max = 0;
	int ret = -1;
	for(int i=0;i<array.length;i++)
	    {
		if(array[i] > max)
		    {
			max = array[i];
			ret = i;
		    }
		else if(array[i] == max)
		    return -2; // 最大が2つ見つかった場合はエラー
	    }
	return ret;
    }
    
    public static int Max(int[] array)
    {
	int max = 0;
	for(int i=0;i<array.length;i++)
	    {
		if(array[i] > max)
		    max = array[i];
	    }
	return max;
    }
    
    public static int Min(int[] array)
    {
	int min = 999999999;
	if (array.length == 0) //空集合の場合は優先度最高
	    return min;
	for(int i=0;i<array.length;i++)
	    {
		if(array[i] < min)
		    min = array[i];
	    }
	return min;
    }
    
    
}
