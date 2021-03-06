(ns nuid.lib)

(defn deep-merge-with
  [f & ms]
  (apply merge-with
         (fn [a b]
           (if (and (map? a) (map? b))
             (deep-merge-with f a b)
             (f a b)))
         ms))

(defn deep-merge
  [& ms]
  (apply deep-merge-with
         (fn [_a b] b)
         ms))

(defn vec-remove-index
  "Throws ClassCastException.
  Throws IndexOutOfBoundsException."
  [v i]
  (into (subvec v 0 i)
        (subvec v (inc i))))

(defn drop-nth
  "Works with any sequence; returns a sequence.
  If `i` is out-of-bounds, returns `coll` as a sequence."
  [coll i]
  (keep-indexed (fn [index x]
                  (when (not (= index i))
                    x))
                coll))

(defn fqn
  "Stringifies a keyword or symbol, maintaining its
  namespace. Returns `nil` for any other input."
  [x]
  (when (or (keyword? x) (symbol? x))
    (if-let [ns (namespace x)]
      (str ns "/" (name x))
      (name x))))
