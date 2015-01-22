/*******************************************************************************
 * Copyright (c) 2013-2015 CWI
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *
 *   * Michael Steindorfer - Michael.Steindorfer@cwi.nl - CWI  
 *******************************************************************************/
package org.eclipse.imp.pdb.facts.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

public interface TransientMap<K, V> extends Map<K, V> {

	boolean containsKey(final java.lang.Object o);

	boolean containsKeyEquivalent(final java.lang.Object o, final Comparator<Object> cmp);

	V get(final java.lang.Object o);

	V getEquivalent(final java.lang.Object o, final Comparator<Object> cmp);

	boolean containsValue(final java.lang.Object o);

	boolean containsValueEquivalent(final java.lang.Object o, final Comparator<Object> cmp);

	V __put(final K key, final V val);

	V __putEquivalent(final K key, final V val, final Comparator<Object> cmp);

	boolean __putAll(final Map<? extends K, ? extends V> map);

	boolean __putAllEquivalent(final Map<? extends K, ? extends V> map, final Comparator<Object> cmp);

	boolean __remove(final K key);

	boolean __removeEquivalent(final K key, final Comparator<Object> cmp);

	Iterator<K> keyIterator();

	Iterator<V> valueIterator();

	Iterator<Map.Entry<K, V>> entryIterator();

	ImmutableMap<K, V> freeze();

}