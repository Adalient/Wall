import java.util.*;
interface Structure{
    // zwraca dowolny element o podanym kolorze
    Optional<Block> findBlockByColor(String color);

    //zwraca wszystkie elementy z danego materiału
    List<Block> findBlocksByMaterial(String material);

    //zwraca liczbę wszystkich elementów tworzących strukturę
    int count();
}
public class Wall implements Structure{
    private List<Block> blocks;
    Wall(List<Block> l){
        blocks = l;
    }
    public Optional<Block> findBlockByColor(String color){
        Optional<Block> b = blocks.stream().filter(block -> block.getColor().equals(color)).findAny();

        //making sure that the returned value is a normal BlockImpl, not CompositeBlockImpl
        if (b.isPresent() && b.get() instanceof CompositeBlockImpl)
            return Optional.of (((CompositeBlockImpl) b.get()).getBlocks().get(0));
        return b;
    }
    public List<Block> findBlocksByMaterial(String material){
        List<Block> temp = new ArrayList<>(blocks.stream().filter(block -> block.getMaterial().equals(material)).toList());

        //replacing CompositeBlock instances with the Blocks that they contain
        List<Block> resultList = new ArrayList<>();
        for (Block block : temp)
            if (block instanceof CompositeBlockImpl)
            {
                resultList.addAll(((CompositeBlockImpl) block).getBlocks());
                temp.remove(block);
            }
        resultList.addAll(temp);
        return resultList;
    }
    public int count(){
        int c = blocks.size();

        //counting CompositeBlocks not as 1, but as the number of Blocks that they contain
        for (Block block : blocks)
            if (block instanceof CompositeBlockImpl)
                c+=((CompositeBlockImpl) block).getBlocks().size()-1;
        return c;
    }
}
interface Block{
    String getColor();
    String getMaterial();
}
interface CompositeBlock extends Block{
    List<Block> getBlocks();
}

//the classes bellow were not added as part of the task, but to test the implemented methods
class BlockImpl implements Block{
    String color;
    String material;
    BlockImpl(String c, String m){
        color = c;
        material = m;
    }
    public String getColor(){
        return color;
    }
    public String getMaterial(){
        return material;
    }
}
class CompositeBlockImpl implements CompositeBlock{
    List<Block> blocks;
    CompositeBlockImpl(List<Block> l){
        if (l.isEmpty())
            throw new IllegalArgumentException("Empty list");
        else
            for (Block b : l)
            {
                if (!Objects.equals(b.getColor(), l.get(0).getColor()))
                    throw new IllegalArgumentException("Color mismatch");
                else if (!Objects.equals(b.getMaterial(), l.get(0).getMaterial()))
                    throw new IllegalArgumentException("Material mismatch");
            }
        blocks = l;
    }
    public List<Block> getBlocks(){
        return blocks;
    }
    public String getColor(){
        return blocks.get(0).getColor();
    }
    public String getMaterial(){
        return blocks.get(0).getMaterial();
    }
}