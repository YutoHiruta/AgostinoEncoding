// ノード
public class Node{
    // ノードID (グラフ定義の際に決定)
    public int node_id;
    // ノード名
    public String node_name;
    
    // 有向辺
    public String[] arrows;
    // 有向辺のノードID (グラフ定義の際に決定)
    public int[] arrows_id;
    
    // コンストラクタ
    public Node(String node_name,String[] arrows)
    {
	this.node_name = node_name;
	this.arrows = arrows;
    }


}
