import java.util.*;

public class Graph{

    public List<Node> nodes;

    public Graph(List<Node> nodes)
    {
	this.nodes = nodes;
	for(int i=0;i<nodes.size();i++)
	    {
		nodes.get(i).node_id = i;
	    }

	
	for(int i=0;i<nodes.size();i++)
	    {
		List<Integer> arrows_id = new ArrayList<>();
		for(int j=0;j<nodes.get(i).arrows.length;j++)
		    {
			for(int k=0;k<nodes.size();k++)
			    if(nodes.get(i).arrows[j].equals(nodes.get(k).node_name))
				{
				    arrows_id.add(nodes.get(k).node_id);
				    break;
				}
			
		    }
		nodes.get(i).arrows_id = arrows_id.stream().mapToInt(x->x).toArray();
	    }
    }
	
    public void Print_graph_status()
    {
	for(int i=0;i<this.nodes.size();i++)
	    {
		System.out.print("ノード " + nodes.get(i).node_name);
		System.out.print("\tノードid " + nodes.get(i).node_id);
		System.out.print("\t高さ " + Height(i));
		System.out.print("\t有向辺(out-neighbor) : ");
		for(int j=0;j<nodes.get(i).arrows.length;j++)
		    {
			System.out.print(nodes.get(i).arrows[j] + " ");
		    }
		System.out.print("\t有向辺(id) : ");
		for(int j=0;j<nodes.get(i).arrows.length;j++)
		    {
			System.out.print(nodes.get(i).arrows_id[j] + " ");
		    }
		int[] in_neighbor = In_neighbor_ids(i);
		System.out.print("\tin-neighbor(id) : ");
		for(int j=0;j<in_neighbor.length;j++)
		    {
			System.out.print(in_neighbor[j] + " ");
		    }
		
		System.out.println();
	    }
	
    }
    
    public int Height(int node_id)
    {
	int focused_node_id = node_id;
	int height = 0;
	int[] layer = nodes.get(focused_node_id).arrows_id;

	if(layer.length <= 0)
	    return 0;
	
	while(true)
	    {
		height += 1;
		List<Integer> next_layer = new ArrayList<>();
		
		for(int i=0;i<layer.length;i++)
		    {
			if(nodes.get(layer[i]).arrows_id.length > 0)
			    for(int j=0;j<nodes.get(layer[i]).arrows_id.length;j++)
				next_layer.add(nodes.get(layer[i]).arrows_id[j]);
		    }
		if (next_layer.size() > 0)
		    layer = next_layer.stream().mapToInt(x->x).toArray();
		else
		    break;
	    }	
	return height;
    }

    public int[] In_neighbor_ids(int node_id)
    {
	List<Integer> arrows_id = new ArrayList<>();
	for(int i=0;i<nodes.size();i++)
	    {
		for(int j=0;j<nodes.get(i).arrows_id.length;j++)
		    if(nodes.get(i).arrows_id[j] == node_id)
			{
			    arrows_id.add(nodes.get(i).node_id);
			    break;
			}
	    }
	return arrows_id.stream().mapToInt(x->x).toArray();
    }

    public int[] Block_of_height(int height)
    {
	List<Integer> return_list = new ArrayList<>();
	for(int i=0;i<nodes.size();i++)
	    {
		if (Height(i) == height)
		    return_list.add(nodes.get(i).node_id);
	    }
	return return_list.stream().mapToInt(x->x).toArray();
    }


    
}
