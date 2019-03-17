package dev.mikit.atcoder.lib.graph.tree;

import dev.mikit.atcoder.lib.graph.Edge;
import dev.mikit.atcoder.lib.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<N, E extends Edge<TreeNode<N, E>, E>> {

    private final List<Node<TreeNode<N, E>, E>> parents = new ArrayList<>(20);
    private N meta;

}