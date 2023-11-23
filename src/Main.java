//program testing the implemented methods

import java.util.*;
public class Main {
    public static void main(String[] args) {

        //testing BlockImpl constructor
        BlockImpl block1 = new BlockImpl("red", "metal");
        assert Objects.equals(block1.getColor(), "red");
        assert Objects.equals(block1.getMaterial(), "metal");

        BlockImpl block2 = new BlockImpl("blue", "brick");
        BlockImpl block3 = new BlockImpl("blue", "plastic");
        BlockImpl block4 = new BlockImpl("green", "wood");
        BlockImpl block5 = new BlockImpl("yellow", "wood");
        BlockImpl block6 = new BlockImpl("red", "brick");
        BlockImpl block7 = new BlockImpl("yellow", "wood");
        BlockImpl block8 = new BlockImpl("white", "metal");
        BlockImpl block9 = new BlockImpl("yellow", "wood");
        BlockImpl block10 = new BlockImpl("red", "metal");

        //testing CompositeBlockImpl constructor's exceptions
        List<Block> l1 = new ArrayList<>();
        l1.add(block4);
        l1.add(block5);
        l1.add(block7);
        l1.add(block9);

        try {
            new CompositeBlockImpl(l1);
        } catch (IllegalArgumentException e){ // color mismatch
            l1.remove(0);
            System.out.println(e);
        }

        List<Block> l2 = new ArrayList<>();
        l2.add(block1);
        l2.add(block6);
        l2.add(block10);

        try {
            new CompositeBlockImpl(l2);
        } catch (IllegalArgumentException e){ // material mismatch
            l2.remove(1);
            System.out.println(e);
        }

        List<Block> l3 = new ArrayList<>();

        try {
            new CompositeBlockImpl(l3);
        } catch (IllegalArgumentException e){ // empty list
            System.out.println(e);
        }

        //testing CompositeBlockImpl constructor
        CompositeBlockImpl comp1 = new CompositeBlockImpl(l1);
        assert Objects.equals(comp1.getColor(), "yellow");
        assert Objects.equals(comp1.getMaterial(), "wood");
        assert comp1.getBlocks().size() == 3;

        CompositeBlockImpl comp2 = new CompositeBlockImpl(l2);
        List<Block> l4 = new ArrayList<>();

        l4.add(block2);
        l4.add(block3);
        l4.add(comp1);
        l4.add(block4);
        l4.add(block6);
        l4.add(comp2);
        l4.add(block8);
        Wall wall = new Wall(l4);

        //testing findBlockByColor method
        Optional<Block> b1 = wall.findBlockByColor("yellow");
        assert Objects.equals(b1.get().getColor(), "yellow");
        assert b1.get() instanceof BlockImpl;
        Optional<Block> b2 = wall.findBlockByColor("pink");
        assert b2.isEmpty();

        //testing findBlocksByMaterial method
        List<Block> l5 = wall.findBlocksByMaterial("metal");
        assert Objects.equals(l5.get(0).getMaterial(), "metal");
        assert l5.get(0) instanceof BlockImpl;
        assert l5.size() == 3;
        List<Block> l6 = wall.findBlocksByMaterial("glass");
        assert l6.isEmpty();

        //testing count method
        assert wall.count() == 10;
    }
}