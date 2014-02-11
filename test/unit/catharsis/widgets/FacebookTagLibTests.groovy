package catharsis.widgets

import grails.test.mixin.TestFor

@TestFor(FacebookTagLib)
class FacebookTagLibTests
{
  void testInitializeTag()
  {
    assert !applyTemplate('<facebook:initialize/>')

    /*def html = applyTemplate('<facebook:initialize appId="appId"/>');
    assert html.contains('<div id="fb-root"></div>')
    assert html.contains('//connect.facebook.net/en_US/all.js#xfbml=1&appId=appId')*/
  }

  void testFollowTag()
  {
    assert !applyTemplate('<facebook:follow/>')
    assert applyTemplate('<facebook:follow url="url"/>') == '<div class="fb-follow" data-href="url"></div>'
    assert applyTemplate("<facebook:follow colorScheme=\"${FacebookColorScheme.DARK}\" url=\"url\" forKids=\"true\" layout=\"${FacebookButtonLayout.BOX_COUNT}\" showFaces=\"true\" width=\"width\" height=\"height\"/>") == '<div class="fb-follow" data-href="url" data-colorscheme="dark" data-kid-directed-site="true" data-layout="box_count" data-show-faces="true" data-width="width" data-height="height"></div>'
  }

  void testLikeTag()
  {
    assert !applyTemplate('<facebook:like/>')
    assert applyTemplate('<facebook:like url="url"/>') == '<div class="fb-like" data-href="url"></div>'
    assert applyTemplate("<facebook:like verb=\"${FacebookLikeButtonVerb.RECOMMEND}\" colorScheme=\"${FacebookColorScheme.DARK}\" url=\"url\" forKids=\"true\" layout=\"${FacebookButtonLayout.BOX_COUNT}\" trackLabel=\"trackLabel\" showFaces=\"true\" width=\"width\"/>") == '<div class="fb-like" data-href="url" data-action="recommend" data-colorscheme="dark" data-kid-directed-site="true" data-layout="box_count" data-ref="trackLabel" data-show-faces="true" data-width="width"></div>'
  }

  void testSendTag()
  {
    assert !applyTemplate('<facebook:send/>')
    assert applyTemplate('<facebook:send url="url"/>') == '<div class="fb-send" data-href="url"></div>'
    assert applyTemplate("<facebook:send url=\"url\" colorScheme=\"${FacebookColorScheme.DARK}\" forKids=\"true\" trackLabel=\"trackLabel\" width=\"width\" height=\"height\"/>") == '<div class="fb-send" data-href="url" data-colorscheme="dark" data-kid-directed-site="true" data-ref="trackLabel" data-width="width" data-height="height"></div>'
  }

  void testPostTag()
  {
    assert !applyTemplate('<facebook:post/>')
    assert applyTemplate('<facebook:post url="url" width="width"/>') == '<div class="fb-post" data-href="url" data-width="width"></div>'
  }

  void testVideoTag()
  {
    assert !applyTemplate('<facebook:video/>')
    assert !applyTemplate('<facebook:video id="id" height="height"/>')
    assert !applyTemplate('<facebook:video id="id" width="width"/>')
    assert !applyTemplate('<facebook:video height="height" width="width"/>')

    assert applyTemplate('<facebook:video video="video" width="width" height="height"/>') == '<iframe frameborder="0" allowfullscreen="true" webkitallowfullscreen="true" mozallowfullscreen="true" width="width" height="height" src="http://www.facebook.com/video/embed?video_id=video"></iframe>'
  }

  void testVideoLinkTag()
  {
    assert !applyTemplate('<facebook:videoLink/>')
    assert applyTemplate('<facebook:videoLink video="video"/>') == '<a href="https://www.facebook.com/photo.php?v=video"></a>'
  }

  void testVideoUrlTag()
  {
    assert !applyTemplate('<facebook:videoUrl/>')
    assert applyTemplate('<facebook:videoUrl video="video"/>') == 'https://www.facebook.com/photo.php?v=video'
  }
}
