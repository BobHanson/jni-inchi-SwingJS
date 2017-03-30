The git repository is cloned from original svn repository, see the project homepage:
http://jni-inchi.sourceforge.net/

"master" branch is for original upstream from svn
"deb" branch is for debianization


If you want to setup svn-remote for fetching the latest changes from
the original SVN repository, add the following lines in .git/config:

[svn-remote "svn"]
        url = https://svn.code.sf.net/p/jni-inchi/code
        fetch = trunk:refs/remotes/svn/trunk
        branches = branches/*:refs/svn/upstream/*
        tags = tags/*:refs/remotes/svn/tags/*
[svn]
        authorsfile = ./authors.txt

Fetch the data from SVN:
$ git svn fetch

After that "git branch -r" will show "svn/<smth>" remote branches.
