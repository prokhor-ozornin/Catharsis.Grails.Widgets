package catharsis.widgets

/**
 * Tumblr tags library
 * @see "http://www.tumblr.com"
 */
class TumblrTagLib
{
  static final String namespace = "tumblr"

  /**
   * Renders Tumblr "Follow" button.
   * @see "http://www.tumblr.com/buttons"
   * @attr account REQUIRED Name of Tumblr account (blog).
   * @attr type Visual layout/appearance of button (TumblrFollowButtonType or string).
   * @attr color_scheme Visual color scheme of button (TumblrFollowButtonColorScheme or string).
   */
  def follow_button = { attrs ->
    if (!attrs.account)
    {
      return
    }

    def type = (attrs.type ?: TumblrFollowButtonType.FIRST).toString()

    def width = 189
    switch (type)
    {
      case TumblrFollowButtonType.SECOND.toString() :
        width = 113
      break

      case TumblrFollowButtonType.THIRD.toString() :
        width = 18
      break
    }

    out << g.withTag(name: "iframe", attrs:
    [
      class: "btn",
      border: "0",
      allowtransparency: true,
      frameborder: "0",
      height: "25",
      width: width,
      scrolling: "no",
      src: "http://platform.tumblr.com/v1/follow_button.html?button_type=${type}&tumblelog=${attrs.account}&color_scheme=${attrs.color_scheme ?: TumblrFollowButtonColorScheme.LIGHT}"
    ])
  }

  /**
   * Renders Tumblr "Share" button.
   * Requires "tumblr" module to be loaded with Resources plugin.
   * @see "http://www.tumblr.com/buttons"
   * @attr type Visual layout/appearance of button (TumblrShareButtonType or string).
   * @attr color_scheme Visual color scheme of button (TumblrShareButtonColorScheme or string).
   */
  def share_button = { attrs ->
    def type = (attrs.type ?: TumblrShareButtonType.FIRST).toString()

    def width
    switch (type)
    {
      case TumblrShareButtonType.FIRST.toString() :
        width = 80
      break

      case TumblrShareButtonType.SECOND.toString() :
        width = 70
      break

      case TumblrShareButtonType.THIRD.toString() :
        width = 130
      break

      case TumblrShareButtonType.FOURTH.toString() :
        width = 20
      break

      default :
        width = 80
      break
    }

    out << g.withTag(name: "a", attrs:
    [
      href: "http://www.tumblr.com/share",
      title: "Share on Tumblr",
      style: "display:inline-block; text-indent:-9999px; overflow:hidden; width:${width}px; height:20px; background:url('http://platform.tumblr.com/v1/share_${type}${attrs.color_scheme?.toString()?.toLowerCase() == TumblrShareButtonColorScheme.GRAY.toString() ? "T" : ""}.png') top left no-repeat transparent;"
    ],
    "Share on Tumblr")
  }
}

enum TumblrFollowButtonColorScheme
{
  LIGHT,
  DARK

  String toString()
  {
    return name().toLowerCase()
  }
}

enum TumblrFollowButtonType
{
  FIRST,
  SECOND,
  THIRD

  String toString()
  {
    return (ordinal() + 1).toString()
  }
}

enum TumblrShareButtonColorScheme
{
  LIGHT,
  GRAY

  String toString()
  {
    return name().toLowerCase()
  }
}

enum TumblrShareButtonType
{
  FIRST,
  SECOND,
  THIRD,
  FOURTH

  String toString()
  {
    return (ordinal() + 1).toString()
  }
}