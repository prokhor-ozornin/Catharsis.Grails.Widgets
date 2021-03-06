package catharsis.widgets

import grails.test.mixin.TestFor

@TestFor(TumblrTagLib)
class TumblrTagLibTests
{
  void test_follow_tag()
  {
    assert !applyTemplate('<tumblr:follow_button/>')
    assert applyTemplate('<tumblr:follow_button account="account"/>') == '<iframe class="btn" border="0" allowtransparency="true" frameborder="0" height="25" width="189" scrolling="no" src="http://platform.tumblr.com/v1/follow_button.html?button_type=1&tumblelog=account&color_scheme=light"></iframe>'
    assert applyTemplate("<tumblr:follow_button account=\"account\" type=\"${TumblrFollowButtonType.SECOND}\" color_scheme=\"${TumblrFollowButtonColorScheme.DARK}\"/>") == '<iframe class="btn" border="0" allowtransparency="true" frameborder="0" height="25" width="113" scrolling="no" src="http://platform.tumblr.com/v1/follow_button.html?button_type=2&tumblelog=account&color_scheme=dark"></iframe>'
  }

  void test_share_tag()
  {
    assert applyTemplate('<tumblr:share_button/>') == '<a href="http://www.tumblr.com/share" title="Share on Tumblr" style="display:inline-block; text-indent:-9999px; overflow:hidden; width:80px; height:20px; background:url(\'http://platform.tumblr.com/v1/share_1.png\') top left no-repeat transparent;">Share on Tumblr</a>'
    assert applyTemplate("<tumblr:share_button type=\"${TumblrShareButtonType.SECOND}\" color_scheme=\"${TumblrShareButtonColorScheme.GRAY}\"/>") == '<a href="http://www.tumblr.com/share" title="Share on Tumblr" style="display:inline-block; text-indent:-9999px; overflow:hidden; width:70px; height:20px; background:url(\'http://platform.tumblr.com/v1/share_2T.png\') top left no-repeat transparent;">Share on Tumblr</a>'
  }
}
