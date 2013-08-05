(defn makeTriangle [rows]

  (def rootNode (first rows))

  )

(defrecord Node [value left right leftParent rightParent])

;;"/problem67/test/data/smallTriangle.txt"
(defn parse [file]
  (def cwd (System/getProperty "user.dir"))
  (def rawFileContent (slurp (str
                               cwd
                               file
                               )
                        )
    )
  (def lines (.split rawFileContent "\n"))
  (makeTriangle lines)
  )

