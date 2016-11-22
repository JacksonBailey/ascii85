Ascii85
=======

This is a very simple project with an Ascii85 codec (decoder & encoder). The version most similar to what is
implemented is the **Adobe Version** except `<~ ~>` is not (currently) supported to fence the encoded text.

This codec supports:

1. Using `u` to pad the last block
1. Using `z` as a short form for all-zero group

In Ascii85-encoded blocks, whitespace and line-break characters may be present anywhere, including in the middle of a
5-character block, but they must be silently ignored.

> Ascii85, also called Base85, is a form of binary-to-text encoding developed by Paul E. Rutter for the btoa utility.
> By using five ASCII characters to represent four bytes of binary data (making the encoded size ¹⁄₄ larger than the
> original, assuming eight bits per ASCII character), it is more efficient than uuencode or Base64, which use four
> characters to represent three bytes of data (¹⁄₃ increase, assuming eight bits per ASCII character).

[Source](https://en.wikipedia.org/wiki/Ascii85)

License
-------

Copyright © 2016 Farid Zakaria

Distributed under the Eclipse Public License

Modifications by Jackson Bailey (© 2016)
