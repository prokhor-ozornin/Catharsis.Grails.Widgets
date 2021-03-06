package catharsis.widgets

import grails.converters.JSON

/**
 * Vkontakte tags library
 * @see "http://vk.com"
 */
class VkontakteTagLib
{
  static final String namespace = "vkontakte"

  /**
   * Renders VKontakte OAuth button widget.
   * Requires "vkontakte" module to be loaded with Resources plugin.
   * @see "http://vk.com/dev/Auth"
   * @attr type Type of authentication mode to use (VkontakteAuthButtonType or string).
   * @attr element_id Identifier of HTML container for the widget.
   * @attr callback Name of JavaScript function to be called after successful authentication, if using dynamic mode.
   * @attr url URL address of web page to be redirected to, if using standard mode.
   * @attr width Horizontal width of button.
   */
  def auth_button = { attrs ->
    if (attrs.type.toString() == VkontakteAuthButtonType.DYNAMIC.toString() && !attrs.callback)
    {
      return
    }

    if ((!attrs.type || attrs.type.toString() == VkontakteAuthButtonType.STANDARD.toString()) && !attrs.url)
    {
      return
    }

    def config = [:]
    if (attrs.callback)
    {
      config.onAuth = attrs.callback
    }
    if (attrs.url)
    {
      config.authUrl = attrs.url
    }
    if (attrs.width)
    {
      config.width = attrs.width
    }

    def element_id = attrs.element_id ?: "vk_auth";

    out << g.withTag(name: "div", attrs: ["id": element_id])
    out << g.javascript(null, "VK.Widgets.Auth(\"${element_id}\", ${config as JSON});")
  }

  /**
   * Renders VKontakte comments widget.
   * Requires "vkontakte" module to be loaded with Resources plugin.
   * @see "http://vk.com/dev/Comments"
   * @attr limit Maximum number of comments to display (VkontakteCommentsLimit or integer).
   * @attr attach Set of attachment types, which are allowed in comment posts (VkontakteCommentsAttach/string or collection of these elements).
   * @attr width Horizontal width of comment area.
   * @attr auto_publish Whether to automatically publish user's comment to his status. Default is TRUE.
   * @attr auto_update Whether to enable automatic update of comments in realtime. Default is TRUE.
   * @attr element_id Identifier of HTML container for the widget.
   * @attr mini Whether to use minimalistic mode of widget (small fonts, images, etc.). Default is to use auto mode (determine automatically).
   */
  def comments = { attrs ->
    def config =
    [
      limit: (attrs.limit ?: VkontakteCommentsLimit.FIVE).toString()
    ]
    if (attrs.attach)
    {
      config.attach = attrs.attach instanceof Collection ? attrs.attach.join(",") : attrs.attach.toString()
    }
    else
    {
      config.attach = false
    }
    if (attrs.width)
    {
      config.width = attrs.width
    }
    if (attrs.auto_publish != null)
    {
      config.autoPublish = attrs.auto_publish.toBoolean() ? 1 : 0;
    }
    if (attrs.auto_update != null)
    {
      config.norealtime = attrs.auto_update.toBoolean() ? 0 : 1;
    }
    if (attrs.mini != null)
    {
      config.mini = attrs.mini.toBoolean() ? 1 : 0;
    }

    def element_id = attrs.element_id ?: "vk_comments";

    out << g.withTag(name: "div", attrs: ["id": element_id])
    out << g.javascript(null, "VK.Widgets.Comments(\"${element_id}\", ${config as JSON});")
  }

  /**
   * Renders VKontakte community widget.
   * Requires "vkontakte" module to be loaded with Resources plugin.
   * @see "http://vk.com/dev/Community"
   * @attr account REQUIRED Identifier or VKontakte public group/community.
   * @attr width Horizontal width of widget.
   * @attr height Vertical height of widget.
   * @attr mode Type of information to be displayed about given community (VkontakteCommunityMode or integer).
   * @attr element_id Identifier of HTML container for the widget.
   * @attr background_color Background color of widget.
   * @attr button_color Button color of widget.
   * @attr text_color Text color of widget.
   */
  def community = { attrs ->
    if (!attrs.account)
    {
      return
    }

    def config = [:]

    config.mode = (attrs.mode ?: VkontakteCommunityMode.PARTICIPANTS).toString()
    if (config.mode == VkontakteCommunityMode.NEWS.toString())
    {
      config.wide = 1
    }
    if (attrs.width)
    {
      config.width = attrs.width
    }
    if (attrs.height)
    {
      config.height = attrs.height
    }
    if (attrs.background_color)
    {
      config.color1 = attrs.background_color
    }
    if (attrs.text_color)
    {
      config.color2 = attrs.text_color
    }
    if (attrs.button_color)
    {
      config.color3 = attrs.button_color
    }

    def element_id = attrs.element_id ?: "vk_groups_${attrs.account}"

    out << g.withTag(name: "div", attrs: ["id": element_id])
    out << g.javascript(null, "VK.Widgets.Group(\"${element_id}\", ${config as JSON}, \"${attrs.account}\");")
  }

  /**
   * Performs initialization of VKontakte JavaScript API. Initialization must be performed before render any VKontakte widgets on web pages.
   * Requires "vkontakte" module to be loaded with Resources plugin.
   * @see "http://vk.com/dev/sites"
   * @attr api_id REQUIRED API identifier of registered VKontakte application.
   */
  def initialize = { attrs ->
    if (!attrs.api_id)
    {
      return
    }

    r.script(disposition: "head", "VK.init({apiId:${attrs.api_id}, onlyWidgets:true});")
  }

  /**
   * Renders VKontakte "Like" button widget.
   * Requires "vkontakte" module to be loaded with Resources plugin.
   * @see "http://vk.com/dev/Like"
   * @attr layout Visual layout/appearance of the button (VkontakteLikeButtonLayout or string).
   * @attr width Width of button in pixels (integer value > 200, default value is 350). Parameter value has meaning only for a button with a text counter (layout = "full").
   * @attr title Title of the page (to display in preview mode for record on the wall).
   * @attr description Description of the page (to display in preview mode for record on the wall).
   * @attr url URL of the page to "like" (this URL will be shown in a record on the wall). Default is URL of the current page.
   * @attr image URL of the thumbnail image (to display in preview mode for record on the wall).
   * @attr text Text to be published on the wall when "Tell to friends" is pressed. Maximum length is 140 characters. Default value equals to page's title.
   * @attr height Vertical height of the button in pixels (VkontakteLikeButtonHeight or string). Default value is "22".
   * @attr verb Type of text to display on the button (VkontakteLikeButtonVerb or integer).
   * @attr element_id Identifier of HTML container for the widget.
   */
  def like_button = { attrs ->
    def config = [:]

    if (attrs.layout)
    {
      config.type = attrs.layout
    }
    if (config.width)
    {
      config.width = attrs.width
    }
    if (attrs.title)
    {
      config.pageTitle = attrs.title
    }
    if (attrs.description)
    {
      config.pageDescription = attrs.description
    }
    if (attrs.url)
    {
      config.pageUrl = attrs.url
    }
    if (attrs.image)
    {
      config.pageImage = attrs.image
    }
    if (attrs.text)
    {
      config.text = attrs.text
    }
    if (attrs.height)
    {
      config.height = attrs.height
    }
    if (attrs.verb)
    {
      config.verb = attrs.verb.toInteger()
    }

    def element_id = attrs.element_id ?: "vk_like"

    out << g.withTag(name: "div", attrs: [id: element_id])
    out << g.javascript(null, "VK.Widgets.Like(\"${element_id}\", ${config as JSON});")
  }

  /**
   * Requires Vkontakte JavaScript initialization to be performed first.
   * Requires "vkontakte" module to be loaded with Resources plugin.
   * @see "http://vk.com/dev/Poll"
   * @attr element_id Identifier of HTML container for the widget.
   * @attr id REQUIRED Unique identifier of poll.
   * @attr url URL address of poll's web page, if it differs from the current one.
   * @attr width Horizontal width of widget.
   */
  def poll = { attrs ->
    if (!attrs.id)
    {
      return
    }

    def config = [:]

    if (attrs.url)
    {
      config.pageUrl = attrs.url
    }
    if (attrs.width)
    {
      config.width = attrs.width
    }

    def element_id = attrs.element_id ?: "vk_poll_${attrs.id}"

    out << g.withTag(name: "div", attrs: [id: element_id])
    out << g.javascript(null, "VK.Widgets.Poll(\"${element_id}\", ${config as JSON}, \"${attrs.id}\");")
  }

  /**
   * Renders VKontakte Wall Post widget.
   * Requires "vkontakte" module to be loaded with Resources plugin.
   * @see "http://vk.com/dev/Post"
   * @attr id REQUIRED Unique identifier of wall's post.
   * @attr owner REQUIRED Unique identifier of Vkontakte wall's owner.
   * @attr hash REQUIRED Unique hash code of wall's post.
   * @attr element_id Identifier of HTML container for the widget.
   * @attr width Width of wall's post. Default is the width of entire screen.
   */
  def post = { attrs ->
    if (!attrs.id || !attrs.owner || !attrs.hash)
    {
      return
    }

    def config = [:]

    if (attrs.width)
    {
      config.width = attrs.width
    }

    def element_id = attrs.element_id ?: "vk_post_${attrs.owner}_${attrs.id}"

    out << g.withTag(name: "div", attrs: [id: element_id])
    out << g.javascript(null, "(function() { window.VK && VK.Widgets && VK.Widgets.Post && VK.Widgets.Post(\"${element_id}\", ${attrs.owner}, ${attrs.id}, \"${attrs.hash}\", ${config as JSON}) || setTimeout(arguments.callee, 50); }());")
  }

  /**
   * Renders VKontakte Recommendations widget.
   * Requires "vkontakte" module to be loaded with Resources plugin.
   * @see "http://vk.com/dev/Recommended"
   * @attr element_id Identifier of HTML container for the widget.
   * @attr limit Maximum number of pages to display initially. Default is 5.
   * @attr max Maximum number of pages to display when "Show all recommendations" is being pressed. Default is 4 * limit.
   * @attr period Report statistical period (VkontakteRecommendationsPeriod or string). Default is "week".
   * @attr sorting Recommended materials sorting mode (VkontakteRecommendationsSorting or string). Default is "friend_likes".
   * @attr target Target attribute for recommendations HTML hyperlinks. Default is "parent".
   * @attr verb Numeric code of verb to use as a label (VkontakteRecommendationsVerb or integer). Default is 0 ("like").
   */
  def recommendations  = { attrs ->
    def config = [:]

    if (attrs.limit)
    {
      config.limit = attrs.limit
    }
    if (attrs.max)
    {
      config.max = attrs.max
    }
    if (attrs.period)
    {
      config.period = attrs.period.toString()
    }
    if (attrs.verb)
    {
      config.verb = attrs.verb.toString()
    }
    if (attrs.sorting)
    {
      config.sort = attrs.sorting.toString()
    }
    if (attrs.target)
    {
      config.target = attrs.target
    }

    def element_id = attrs.element_id ?: "vk_recommendations"

    out << g.withTag(name: "div", attrs: [id: element_id])
    out << g.javascript(null, "VK.Widgets.Recommended(\"${element_id}\", ${config as JSON});")
  }

  /**
   * Renders VKontakte page subscription widget.
   * Requires "vkontakte" module to be loaded with Resources plugin.
   * @see "http://vk.com/dev/Subscribe"
   * @attr account REQUIRED Identifier of user/group to subscribe to.
   * @attr layout Visual layout/appearance of the button (VkontakteSubscriptionButtonLayout or integer).
   * @attr only_button Whether to display both author and button (false) or button only (true).
   * @attr element_id Identifier of HTML container for the widget.
   */
  def subscription = { attrs ->
    if (!attrs.account)
    {
      return
    }

    def config =
    [
      mode : (attrs.layout ?: VkontakteSubscriptionButtonLayout.BUTTON).toString()
    ]
    if (attrs.only_button?.toBoolean())
    {
      config.soft = 1
    }

    def element_id = attrs.element_id ?: "vk_subscribe_${attrs.account}"

    out << g.withTag(name: "div", attrs: [id: element_id])
    out << g.javascript(null, "VK.Widgets.Subscribe(\"${element_id}\", ${config as JSON}, \"${attrs.account}\");")
  }

  /**
   * Renders embedded VKontakte video on web page.
   * @attr id REQUIRED Identifier of video.
   * @attr user REQUIRED Account identifier of video's uploader.
   * @attr hash REQUIRED Hash code of video.
   * @attr width REQUIRED Width of video control.
   * @attr height REQUIRED Height of video control.
   * @attr hd Whether to play video in High Definition format. Default is false.
   */
  def video = { attrs ->
    if (!attrs.id || !attrs.user || !attrs.hash || !attrs.width || !attrs.height)
    {
      return
    }

    out << g.withTag(name: "iframe", attrs:
    [
      frameborder: "0",
      allowfullscreen: true,
      webkitallowfullscreen: true,
      mozallowfullscreen: true,
      width: attrs.width,
      height: attrs.height,
      src: "http://vk.com/video_ext.php?oid=${attrs.user}&id=${attrs.id}&hash=${attrs.hash}&hd=${attrs.hd?.toBoolean() ? 1 : 0}"
    ])
  }
}

/**
 * Vkontakte OAuth authentification mode.
 */
enum VkontakteAuthButtonType
{
  /**
   * After authentication specified JavaScript function will be called.
   */
  DYNAMIC,

  /**
   * After authentication user will be redirected to the specified URL.
   */
  STANDARD

  String toString()
  {
    return name().toLowerCase()
  }
}

enum VkontakteCommentsAttach
{
  /**
   * All types of attachments are allowed
   */
  ALL,

  /**
   * Grafitti
   */
  GRAFFITI,

  /**
   * Photos
   */
  PHOTO,

  /**
   * Videos
   */
  VIDEO,

  /**
   * Audio (songs)
   */
  AUDIO,

  /**
   * Web links
   */
  LINK

  String toString()
  {
    switch (this)
    {
      case ALL :
        return "*"
        break

      default :
        return name().toLowerCase()
        break
    }
  }
}

enum VkontakteCommentsLimit
{
  /**
   * 5
   */
  FIVE,

  /**
   * 10
   */
  TEN,

  /**
   * 15
   */
  FIFTEEN,

  /**
   * 20
   */
  TWENTY

  String toString()
  {
    switch (this)
    {
      case FIVE :
        return "5"
        break

      case TEN :
        return "10"
        break

      case FIFTEEN :
        return "15"
        break

      case TWENTY :
        return "20"
        break
    }
  }
}

enum VkontakteCommunityMode
{
  /**
   * Display community participants
   */
  PARTICIPANTS,

  /**
   * Display only community title
   */
  TITLE,

  /**
   * Display recent community news
   */
  NEWS

  String toString()
  {
    return ordinal().toString()
  }
}

enum VkontakteLikeButtonHeight
{
  /**
   * 18px
   */
  HEIGHT_18,

  /**
   * 20px
   */
  HEIGHT_20,

  /**
   * 22px
   */
  HEIGHT_22,

  /**
   * 24px
   */
  HEIGHT_24

  String toString()
  {
    switch (this)
    {
      case HEIGHT_18 :
        return "18"
        break

      case HEIGHT_20 :
        return "20"
        break

      case HEIGHT_22 :
        return "22"
        break

      case HEIGHT_24 :
        return "24"
        break
    }
  }
}

enum VkontakteLikeButtonLayout
{
  /**
   * Button with a text counter
   */
  FULL,

  /**
   * Button with a micro counter
   */
  BUTTON,

  /**
   * Micro button
   */
  MINI,

  /**
   * Micro button, counter is over the button
   */
  VERTICAL

  String toString()
  {
    return name().toLowerCase()
  }
}

enum VkontakteLikeButtonVerb
{
  /**
   * "I like"
   */
  LIKE,

  /**
   * "It's interesting"
   */
  INTEREST

  String toString()
  {
    return ordinal().toString()
  }
}

enum VkontakteRecommendationsPeriod
{
  DAY,

  WEEK,

  MONTH

  String toString()
  {
    return name().toLowerCase()
  }
}

enum VkontakteRecommendationsSorting
{
  FRIEND_LIKES,

  LIKES

  String toString()
  {
    return name().toLowerCase()
  }
}

enum VkontakteRecommendationsVerb
{
  LIKE,

  INTEREST

  String toString()
  {
    return ordinal().toString()
  }
}

enum VkontakteSubscriptionButtonLayout
{
  BUTTON,
  LIGHT_BUTTON,
  LINK

  String toString()
  {
    return ordinal().toString()
  }
}
