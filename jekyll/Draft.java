package jekyll;

import java.io.File;

public class Draft extends Post
{
	public Draft( File file, Blog blog )
	{
		super( file, blog );
	}
	
	public Draft( String title, Blog blog )
	{
		super( title, blog );
	}
	
	public boolean publish()
	{
		PublishedPost post = new PublishedPost( this, this.blog );
		
		if ( post.save() )
			return this.delete();
		
		return false;
	}
	
	public static String parseTitleFromFilename( String filename )
	{
		return Post.parseTitleFromFilename( filename, false );
	}
	
	@Override
	public boolean save()
	{
		this.postDir = this.blog.getDraftsDir();
		
		return super.save();
	}
	
	@Override
	protected void parseFrontMatter()
	{
	}

	@Override
	protected String generateFilename()
	{
		return Post.getFilenameFromTitle( this.getTitle() );
	}

	@Override
	protected String generateFrontMatter()
	{
		return "";
	}

	@Override
	public PostType getType()
	{
		return PostType.DRAFT;
	}
}
