package net.consensys.pantheon.ethereum.trie;

import static net.consensys.pantheon.crypto.Hash.keccak256;

import net.consensys.pantheon.ethereum.rlp.RLP;
import net.consensys.pantheon.util.bytes.Bytes32;
import net.consensys.pantheon.util.bytes.BytesValue;

import java.util.Optional;

class NullNode<V> implements Node<V> {
  private static final Bytes32 HASH = keccak256(RLP.NULL);

  @SuppressWarnings("rawtypes")
  private static final NullNode instance = new NullNode();

  private NullNode() {}

  @SuppressWarnings("unchecked")
  static <V> NullNode<V> instance() {
    return instance;
  }

  @Override
  public Node<V> accept(final PathNodeVisitor<V> visitor, final BytesValue path) {
    return visitor.visit(this, path);
  }

  @Override
  public void accept(final NodeVisitor<V> visitor) {
    visitor.visit(this);
  }

  @Override
  public BytesValue getPath() {
    return BytesValue.EMPTY;
  }

  @Override
  public Optional<V> getValue() {
    return Optional.empty();
  }

  @Override
  public BytesValue getRlp() {
    return RLP.NULL;
  }

  @Override
  public BytesValue getRlpRef() {
    return RLP.NULL;
  }

  @Override
  public Bytes32 getHash() {
    return HASH;
  }

  @Override
  public Node<V> replacePath(final BytesValue path) {
    return this;
  }

  @Override
  public String print() {
    return "[NULL]";
  }

  @Override
  public boolean isDirty() {
    return false;
  }

  @Override
  public void markDirty() {
    // do nothing
  }
}