package catharsis.widgets

/**
 * Gravatar tags library
 * @see "http://gravatar.org"
 */
class GravatarTagLib
{
  static final String namespace = "gravatar"

  /**
   * Returns Gravatar's avatar image URL.
   * @see "http://gravatar.com/site/implement/images"
   * @attr email Email address of the user whose avatar is requested.
   * @attr hash MD5 hash of user's email address.
   * @attr extension File-type extension for URL (jpg, png, gif, etc).
   * @attr default Default image to be returned when user's email address has no matching Gravatar image (GravatarDefaultImage or string).
   * @attr force_default Forces default image to be loaded as a user's avatar.
   * @attr rating Rating of avatar's image that represents audience restrictions.
   * @attr size Size of avatar's image in pixels (both width and height).
   * @attr parameters Map of additional parameters for URL query part.
   */
  def image_url = { attrs ->
    if (!attrs.email && !attrs.hash)
    {
      return
    }

    def parameters = [:]
    if (attrs.parameters)
    {
      parameters.putAll(attrs.parameters)
    }
    if (attrs.default)
    {
      parameters.default = attrs.default.toString()
    }
    if (attrs.force_default?.toBoolean())
    {
      parameters.forcedefault = "y"
    }
    if (attrs.rating)
    {
      parameters.rating = attrs.rating.toString()
    }
    if (attrs.size)
    {
      parameters.size = attrs.size.toString()
    }

    def hash = attrs.hash ?: attrs.email.trim().toLowerCase().encodeAsMD5()
    def query = parameters.collect { pair -> "${pair.key.encodeAsURL()}=${pair.value.encodeAsURL()}" }.join("&")

    out << "http://www.gravatar.com/avatar/${hash}${attrs.extension ? ".${attrs.extension}" : ""}${query ? "?${query}" : ""}"
  }

  /**
   * Returns Gravatar's user profile URL.
   * @see "http://gravatar.com/site/implement/profiles"
   * @attr email Email address of the user whose profile is requested.
   * @attr hash MD5 hash of user's email address.
   * @attr format Format in which to retrieve profile's data.
   * @attr parameters Map of additional parameters for URL query part.
   */
  def profile_url = { attrs ->
    if (!attrs.email && !attrs.hash)
    {
      return
    }

    def hash = attrs.hash ?: attrs.email.trim().toLowerCase().encodeAsMD5()
    def query = attrs.parameters.collect { pair -> "${pair.key.encodeAsURL()}=${pair.value.encodeAsURL()}" }.join("&")

    out << "http://www.gravatar.com/${hash}${attrs.format ? ".${attrs.format}" : ""}${query ? "?${query}" : ""}"
  }
}

/**
 * Type of default image for Gravatar's avatar.
 */
enum GravatarDefaultImage
{
  /**
   * Do not load any image if none is associated with the email hash, instead return an HTTP 404 (File Not Found) response.
   */
  NOT_FOUND,

  /**
   * A simple, cartoon-style silhouetted outline of a person (does not vary by email hash).
   */
  MYSTERY_MAN,

  /**
   * A geometric pattern based on an email hash.
   */
  IDENT_ICON,

  /**
   * A generated 'monster' with different colors, faces, etc.
   */
  MONSTER_ID,

  /**
   * Generated faces with differing features and backgrounds.
   */
  WAVATAR,

  /**
   * Awesome generated, 8-bit arcade-style pixelated faces.
   */
  RETRO,

  /**
   * A transparent PNG image (border added to HTML below for demonstration purposes).
   */
  BLANK

  String toString()
  {
    switch (this)
    {
      case MYSTERY_MAN :
        return "mm"

      case IDENT_ICON :
        return "identicon"

      case MONSTER_ID :
        return "monsterid";

      case WAVATAR :
        return "wavatar";

      case RETRO :
        return "retro";

      case BLANK :
        return "blank";

      default:
        return "404"
    }
  }
}

/**
 * Rate of image that indicates whether it's appropriate for a certain audience.
 */
enum GravatarImageRating
{
  /**
   * Suitable for display on all websites with any audience type.
   */
  G,

  /**
   * May contain rude gestures, provocatively dressed individuals, the lesser swear words, or mild violence.
   */
  PG,

  /**
   * May contain such things as harsh profanity, intense violence, nudity, or hard drug use.
   */
  R,

  /**
   * May contain hardcore sexual imagery or extremely disturbing violence.
   */
  X

  String toString()
  {
    return name().toLowerCase()
  }
}

/**
 * Format of Gravatar user's profile data.
 */
enum GravatarProfileFormat
{
  /**
   * JSON
   */
  JSON,

  /**
   * XML
   */
  XML,

  /**
   * PHP
   */
  PHP,

  /**
   * VCF
   */
  VCF,

  /**
   * QR code
   */
  QR

  String toString()
  {
    return name().toLowerCase()
  }
}